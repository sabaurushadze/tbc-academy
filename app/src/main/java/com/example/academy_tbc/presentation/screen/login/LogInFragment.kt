package com.example.academy_tbc.presentation.screen.login

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.academy_tbc.AuthApplication
import com.example.academy_tbc.R
import com.example.academy_tbc.data.auth.login.RequestLoginDto
import com.example.academy_tbc.databinding.FragmentLogInBinding
import com.example.academy_tbc.presentation.common.BaseFragment
import com.example.academy_tbc.presentation.common.ViewModelFactory
import com.example.academy_tbc.presentation.extension.showSnackBar
import com.example.academy_tbc.presentation.screen.login.state.LogInField
import com.example.academy_tbc.presentation.screen.login.state.LogInFieldError
import com.example.academy_tbc.presentation.screen.login.state.LogInValidationError
import kotlinx.coroutines.launch

class LogInFragment : BaseFragment<FragmentLogInBinding>(
    FragmentLogInBinding::inflate
) {
    private val viewModel: LogInViewModel by viewModels {
        ViewModelFactory {
            val application = requireActivity().application as AuthApplication
            LogInViewModel(
                networkAuthRepository = application.container.authRepository,
                userTokenRepository = application.container.userTokenRepository
            )
        }
    }

    override fun listeners() {
        observe()
        onLoginClick()
    }

    private fun observe() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.loginUiState.collect { uiState ->
                    if (uiState.isLoading) {
                        showLoading()
                    }

                    uiState.error?.let { error ->
                        handleError(error)
                    }

                    uiState.isLoggedIn?.let { loggedIn ->
                        if (loggedIn) {
                            handleSuccess()
                        }
                    }
                }
            }
        }
    }

    private fun handleSuccess() = with(binding) {
        btnLogin.isEnabled = false
        viewModel.resetState()
        clearInputs()
        progressBar.isVisible = false
        findNavController().navigate(
            LogInFragmentDirections.actionLogInFragmentToHomeFragment()
        )
    }

    private fun handleError(error: LogInValidationError) = with(binding) {
        viewModel.resetState()
        clearInputs()
        progressBar.isVisible = false

        val message = when (error) {
            LogInValidationError.EXCEPTION_NETWORK -> getString(R.string.no_internet_connection_please_try_again)
            LogInValidationError.EXCEPTION_USER_NOT_FOUND -> getString(R.string.user_not_found)
            LogInValidationError.EXCEPTION_UNKNOWN -> getString(R.string.something_went_wrong_please_try_again)
        }
        root.showSnackBar(message)
    }

    private fun showLoading() {
        binding.progressBar.isVisible = true
    }


    private fun onLoginClick() = with(binding) {
        btnLogin.setOnClickListener {
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()

            val validateLogInData = viewModel.validateLogInData(email = email, password = password)
            val isValidData = validateLogInData.first
            val errors = validateLogInData.second

            if (isValidData) {
                val requestLoginDto = RequestLoginDto(
                    email = email, password = password
                )
                viewModel.login(requestLoginDto)
            } else {
                errors.forEach { (field, error) ->
                    val message = when (error) {
                        LogInFieldError.INVALID_EMAIL -> getString(R.string.invalid_email)
                        LogInFieldError.INVALID_PASSWORD -> getString(R.string.invalid_password)
                    }

                    when (field) {
                        LogInField.EMAIL -> etEmail.error = message
                        LogInField.PASSWORD -> etPassword.error = message
                    }
                }
            }
        }
    }

    private fun clearInputs() = with(binding) {
        etEmail.text?.clear()
        etPassword.text?.clear()
    }
}
