package com.example.academy_tbc.presentation.screen.login

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.academy_tbc.R
import com.example.academy_tbc.data.auth.login.RequestLoginDto
import com.example.academy_tbc.databinding.FragmentLogInBinding
import com.example.academy_tbc.presentation.common.BaseFragment
import com.example.academy_tbc.presentation.extension.showSnackBar
import kotlinx.coroutines.launch

class LogInFragment : BaseFragment<FragmentLogInBinding>(
    FragmentLogInBinding::inflate
) {
    private val viewModel: LogInViewModel by viewModels { LogInViewModel.Factory }

    override fun listeners() {
        observe()
        onLoginClick()
    }

    private fun observe() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.loginUiState.collect { uiState ->
                    loginErrorMessages(uiState)
                }
            }
        }
    }

    private fun loginErrorMessages(uiState: LogInUiState) = with(binding) {
        when (uiState) {
            is LogInUiState.Success -> {
                clearInputs()
                viewModel.resetState()
                progressBar.isVisible = false
                findNavController().navigate(
                    LogInFragmentDirections.actionLogInFragmentToHomeFragment()
                )
            }

            is LogInUiState.Error -> {
                clearInputs()
                viewModel.resetState()
                progressBar.isVisible = false

                when (uiState.message) {
                    LogInExceptionErrors.EXCEPTION_NETWORK -> {
                        root.showSnackBar(getString(R.string.no_internet_connection_please_try_again))
                    }

                    LogInExceptionErrors.EXCEPTION_CREDENTIALS -> {
                        root.showSnackBar(getString(R.string.invalid_credentials))
                    }

                    LogInExceptionErrors.EXCEPTION_USER_NOT_FOUND -> {
                        root.showSnackBar(getString(R.string.user_not_found))
                    }

                    LogInExceptionErrors.EXCEPTION_UNKNOWN -> {
                        root.showSnackBar(getString(R.string.something_went_wrong_please_try_again))
                    }
                }
            }

            is LogInUiState.Loading -> {
                progressBar.isVisible = true
                viewModel.resetState()
            }

            is LogInUiState.Idle -> {}
        }
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
                        LogInFieldErrors.INVALID_EMAIL -> getString(R.string.invalid_email)
                        LogInFieldErrors.INVALID_PASSWORD -> getString(R.string.invalid_password)
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
