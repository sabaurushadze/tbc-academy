package com.example.academy_tbc.screen.login

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.academy_tbc.R
import com.example.academy_tbc.common.BaseFragment
import com.example.academy_tbc.databinding.FragmentLogInBinding
import com.example.academy_tbc.extension.showSnackBar
import kotlinx.coroutines.launch

class LogInFragment : BaseFragment<FragmentLogInBinding>(
    FragmentLogInBinding::inflate
) {
    val viewModel: LogInViewModel by viewModels { LogInViewModel.Factory }

    override fun listeners() {
        observe()
        onLoginClick()
    }

    private fun observe() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.loginUiState.collect { uiState ->
                    when (uiState) {
                        is LogInUiState.Success -> {
                            clearInputs()
                            viewModel.resetState()
                            binding.progressBar.isVisible = false
                            binding.root.showSnackBar(getString(R.string.successfully_logged_in))
                        }

                        LogInUiState.Error -> {
                            clearInputs()
                            viewModel.resetState()
                            binding.progressBar.isVisible = false
                            binding.root.showSnackBar(getString(R.string.invalid_credentials))
                        }

                        LogInUiState.Loading -> {
                            binding.progressBar.isVisible = true
                            viewModel.resetState()
                        }

                        LogInUiState.Idle -> {}
                    }
                }
            }
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
                val loginDto = LoginDto(
                    email = email, password = password
                )
                viewModel.login(loginDto)
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
