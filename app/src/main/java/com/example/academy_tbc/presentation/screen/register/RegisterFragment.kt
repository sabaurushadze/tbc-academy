package com.example.academy_tbc.presentation.screen.register

import android.widget.EditText
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.setFragmentResult
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
import kotlinx.coroutines.launch

class RegisterFragment : BaseFragment<FragmentRegisterBinding>(
    FragmentRegisterBinding::inflate
) {
    private val viewModel: RegisterViewModel by viewModels {
        ViewModelFactory {
            val application = requireActivity().application as AuthApplication
            RegisterViewModel(networkAuthRepository = application.container.authRepository)
        }
    }

    override fun listeners() {
        observe()
        observeNavigation()
        validateFieldsAndEnableButton()
        onRegisterClick()
        onBackPressed()
    }

    private fun observeNavigation() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.navigationEvent.collect { event ->
                    when (event) {
                        RegisterNavigationEvent.NavigateToLogin -> {
                            findNavController().navigate(
                                RegisterFragmentDirections.actionRegisterFragmentToLogInFragment()
                            )
                        }
                    }
                }
            }
        }
    }

    private fun observe() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.registerUiState.collect { uiState ->
                    when (uiState) {
                        is RegisterUiState.Success -> {
                            if (uiState.isRegistered) {
                                handleSuccess()
                            }
                        }

                        is RegisterUiState.Error -> handleError(uiState.error)
                        is RegisterUiState.Loading -> showLoading()
                    }
                }
            }
        }
    }

    private fun handleSuccess() = with(binding) {
        viewModel.resetState()
        progressBar.isVisible = false

        val email = etEmail.text.toString()
        val password = etPassword.text.toString()

        setFragmentResult(REQ_KEY_EMAIL, bundleOf(BUNDLE_KEY_EMAIL to email))
        setFragmentResult(REQ_KEY_PASSWORD, bundleOf(BUNDLE_KEY_PASSWORD to password))
        clearInputs()
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

    private fun validateFieldsAndEnableButton() = with(binding) {
        btnRegister.isEnabled = false
        afterTextFieldChanged(etEmail)
        afterTextFieldChanged(etPassword)
        afterTextFieldChanged(etRepeatPassword)
    }

    private fun afterTextFieldChanged(
        etRegisterField: EditText,
    ) = with(binding) {
        etRegisterField.doAfterTextChanged {
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()
            val repeatPassword = etRepeatPassword.text.toString()
            val isValidEmailAndPassword = viewModel.validateRegisterData(email, password)

            if (isValidEmailAndPassword && password == repeatPassword) {
                btnRegister.isEnabled = true
                btnRegister.backgroundTintList =
                    ContextCompat.getColorStateList(requireContext(), R.color.primary)
            } else {
                btnRegister.isEnabled = false
                btnRegister.backgroundTintList =
                    ContextCompat.getColorStateList(requireContext(), R.color.primaryDisabled)
            }
        }
    }

    private fun onRegisterClick() = with(binding) {
        btnRegister.setOnClickListener {
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()

            val requestRegisterDto = RequestRegisterDto(email = email, password = password)
            viewModel.register(user = requestRegisterDto)
        }
    }

    private fun onBackPressed() {
        binding.ibBack.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun clearInputs() = with(binding) {
        etEmail.text?.clear()
        etPassword.text?.clear()
        etRepeatPassword.text?.clear()
    }

    companion object {
        const val REQ_KEY_EMAIL = "reqKeyEmail"
        const val REQ_KEY_PASSWORD = "reqKeyPassword"
        const val BUNDLE_KEY_EMAIL = "bundleKeyEmail"
        const val BUNDLE_KEY_PASSWORD = "bundleKeyPassword"
    }
}