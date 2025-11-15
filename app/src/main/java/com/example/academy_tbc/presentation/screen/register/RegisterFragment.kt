package com.example.academy_tbc.presentation.screen.register

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.academy_tbc.AuthApplication
import com.example.academy_tbc.R
import com.example.academy_tbc.data.auth.register.RequestRegisterDto
import com.example.academy_tbc.databinding.FragmentRegisterBinding
import com.example.academy_tbc.presentation.common.BaseFragment
import com.example.academy_tbc.presentation.common.ViewModelFactory
import com.example.academy_tbc.presentation.extension.showSnackBar
import com.example.academy_tbc.presentation.screen.register.state.RegisterField
import com.example.academy_tbc.presentation.screen.register.state.RegisterFieldError
import com.example.academy_tbc.presentation.screen.register.state.RegisterValidationError
import kotlinx.coroutines.launch

class RegisterFragment : BaseFragment<FragmentRegisterBinding>(
    FragmentRegisterBinding::inflate
) {
    private val viewModel: RegisterViewModel by viewModels {
        ViewModelFactory {
            val application = requireActivity().application as AuthApplication
            RegisterViewModel(
                networkAuthRepository = application.container.authRepository,
                userTokenRepository = application.container.userTokenRepository
            )
        }
    }

    override fun listeners() {
        observe()
        onRegisterClick()
    }

    private fun observe() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.registerUiState.collect { uiState ->
                    if (uiState.isLoading) {
                        showLoading()
                    }

                    uiState.error?.let { error ->
                        handleError(error)
                    }

                    uiState.isRegistered?.let { registered ->
                        if (registered) {
                            handleSuccess()
                        }
                    }
                }
            }
        }
    }

    private fun handleSuccess() = with(binding) {
        btnRegister.isEnabled = false
        viewModel.resetState()
        clearInputs()
        progressBar.isVisible = false
        findNavController().navigate(
            RegisterFragmentDirections.actionRegisterFragmentToHomeFragment()
        )
    }

    private fun handleError(error: RegisterValidationError) = with(binding) {
        viewModel.resetState()
        clearInputs()
        progressBar.isVisible = false

        val message = when (error) {
            RegisterValidationError.EXCEPTION_NETWORK -> getString(R.string.no_internet_connection_please_try_again)
            RegisterValidationError.EXCEPTION_USER_NOT_FOUND -> getString(R.string.user_with_this_email_cannot_be_registered)
            RegisterValidationError.EXCEPTION_UNKNOWN -> getString(R.string.something_went_wrong_please_try_again)
        }
        root.showSnackBar(message)
    }

    private fun showLoading() {
        binding.progressBar.isVisible = true
    }


    private fun onRegisterClick() = with(binding) {
        btnRegister.setOnClickListener {
            val email = etEmail.text.toString()
            val userName = etUsername.text.toString()
            val password = etPassword.text.toString()

            val validateLogInData = viewModel.validateRegisterData(
                email = email, password = password, userName = userName
            )
            val isValidData = validateLogInData.first
            val errors = validateLogInData.second

            if (isValidData) {
                val requestRegisterDto = RequestRegisterDto(
                    email = email, password = password
                )
                viewModel.register(requestRegisterDto)
            } else {
                errors.forEach { (field, error) ->
                    val message = when (error) {
                        RegisterFieldError.INVALID_EMAIL -> getString(R.string.invalid_email)
                        RegisterFieldError.INVALID_PASSWORD -> getString(R.string.invalid_password)
                        RegisterFieldError.INVALID_USERNAME -> getString(R.string.invalid_user_name)
                    }

                    when (field) {
                        RegisterField.EMAIL -> etEmail.error = message
                        RegisterField.PASSWORD -> etPassword.error = message
                        RegisterField.USERNAME -> etUsername.error = message
                    }
                }
            }
        }
    }

    private fun clearInputs() = with(binding) {
        etUsername.text?.clear()
        etEmail.text?.clear()
        etPassword.text?.clear()
    }
}