package com.example.academy_tbc.screen.register

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.academy_tbc.R
import com.example.academy_tbc.common.BaseFragment
import com.example.academy_tbc.databinding.FragmentRegisterBinding
import com.example.academy_tbc.extension.showSnackBar
import kotlinx.coroutines.launch


class RegisterFragment : BaseFragment<FragmentRegisterBinding>(
    FragmentRegisterBinding::inflate
) {
    val viewModel: RegisterViewModel by viewModels { RegisterViewModel.Factory }
    override fun bind() {}
    override fun listeners() {
        observe()
        onRegisterClick()
    }

    private fun observe() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.registerUiState.collect { uiState ->
                    when (uiState) {
                        is RegisterUiState.Success -> {
                            clearInputs()
                            viewModel.resetState()
                            binding.progressBar.isVisible = false
                            binding.root.showSnackBar(getString(R.string.successfully_registered))
                        }

                        RegisterUiState.Error -> {
                            clearInputs()
                            viewModel.resetState()
                            binding.progressBar.isVisible = false
                            binding.root.showSnackBar(getString(R.string.registration_failed))
                        }

                        RegisterUiState.Loading -> {
                            binding.progressBar.isVisible = true
                            viewModel.resetState()
                        }

                        RegisterUiState.Idle -> {}
                    }
                }
            }
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
                val registerDto = RegisterDto(
                    email = email, password = password
                )
                viewModel.register(registerDto)
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