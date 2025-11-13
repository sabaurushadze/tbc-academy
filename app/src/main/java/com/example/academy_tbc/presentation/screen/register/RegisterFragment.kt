package com.example.academy_tbc.presentation.screen.register

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.academy_tbc.R
import com.example.academy_tbc.data.auth.register.RequestRegisterDto
import com.example.academy_tbc.databinding.FragmentRegisterBinding
import com.example.academy_tbc.presentation.common.BaseFragment
import com.example.academy_tbc.presentation.extension.showSnackBar
import kotlinx.coroutines.launch


class RegisterFragment : BaseFragment<FragmentRegisterBinding>(
    FragmentRegisterBinding::inflate
) {
    private val viewModel: RegisterViewModel by viewModels { RegisterViewModel.Factory }

    override fun listeners() {
        observe()
        onRegisterClick()
    }

    private fun observe() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.registerUiState.collect { uiState ->
                    registerErrorMessages(uiState)
                }
            }
        }
    }

    private fun registerErrorMessages(uiState: RegisterUiState) = with(binding) {
        when (uiState) {
            is RegisterUiState.Success -> {
                clearInputs()
                viewModel.resetState()
                binding.progressBar.isVisible = false
                findNavController().navigate(
                    RegisterFragmentDirections.actionRegisterFragmentToHomeFragment()
                )
            }

            is RegisterUiState.Error -> {
                clearInputs()
                viewModel.resetState()
                binding.progressBar.isVisible = false

                when (uiState.message) {
                    RegisterExceptionErrors.EXCEPTION_NETWORK -> {
                        root.showSnackBar(getString(R.string.no_internet_connection_please_try_again))
                    }

                    RegisterExceptionErrors.EXCEPTION_CREDENTIALS -> {
                        root.showSnackBar(getString(R.string.invalid_credentials))
                    }

                    RegisterExceptionErrors.EXCEPTION_USER_NOT_FOUND -> {
                        root.showSnackBar(getString(R.string.user_not_found))
                    }

                    RegisterExceptionErrors.EXCEPTION_UNKNOWN -> {
                        root.showSnackBar(getString(R.string.something_went_wrong_please_try_again))
                    }
                }
            }

            is RegisterUiState.Loading -> {
                viewModel.resetState()
                binding.progressBar.isVisible = true
            }

            is RegisterUiState.Idle -> {}
        }
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
                        RegisterFieldErrors.INVALID_EMAIL -> getString(R.string.invalid_email)
                        RegisterFieldErrors.INVALID_PASSWORD -> getString(R.string.invalid_password)
                        RegisterFieldErrors.INVALID_USERNAME -> getString(R.string.invalid_user_name)
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