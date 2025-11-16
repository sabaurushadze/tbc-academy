package com.example.academy_tbc.presentation.screen.login

import android.os.Bundle
import android.widget.EditText
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.setFragmentResultListener
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
import com.example.academy_tbc.presentation.screen.register.RegisterFragment.Companion.BUNDLE_KEY_EMAIL
import com.example.academy_tbc.presentation.screen.register.RegisterFragment.Companion.BUNDLE_KEY_PASSWORD
import com.example.academy_tbc.presentation.screen.register.RegisterFragment.Companion.REQ_KEY_EMAIL
import com.example.academy_tbc.presentation.screen.register.RegisterFragment.Companion.REQ_KEY_PASSWORD
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFragmentResultListener(REQ_KEY_EMAIL) { requestKey, bundle ->
            val email = bundle.getString(BUNDLE_KEY_EMAIL)
            email?.let {
                binding.etEmail.setText(it)
            }
        }

        setFragmentResultListener(REQ_KEY_PASSWORD) { requestKey, bundle ->
            val password = bundle.getString(BUNDLE_KEY_PASSWORD)
            password?.let {
                binding.etPassword.setText(it)
            }
        }
    }

    override fun listeners() {
        observe()
        observeNavigation()
        validateFieldsAndEnableButton()
        onLoginClick()
    }

    private fun observeNavigation() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.navigationEvent.collect { event ->
                    when (event) {
                        LogInNavigationEvent.NavigateToHome -> {
                            findNavController().navigate(
                                LogInFragmentDirections.actionLogInFragmentToHomeFragment()
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
                viewModel.loginUiState.collect { uiState ->
                    when (uiState) {
                        is LogInUiState.Success -> {
                            if (uiState.isLoggedIn) {
                                handleSuccess()
                            }
                        }

                        is LogInUiState.Error -> handleError(uiState.error)
                        is LogInUiState.Loading -> showLoading()
                    }
                }
            }
        }
    }

    private fun handleSuccess() = with(binding) {
        viewModel.resetState()
        progressBar.isVisible = false
        clearInputs()
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

    private fun validateFieldsAndEnableButton() = with(binding) {
        btnLogin.isEnabled = false
        afterTextFieldChanged(etEmail)
        afterTextFieldChanged(etPassword)
    }

    private fun afterTextFieldChanged(
        etRegisterField: EditText,
    ) = with(binding) {
        etRegisterField.doAfterTextChanged {
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()
            val isValidEmailAndPassword = viewModel.validateLogInData(email, password)

            if (isValidEmailAndPassword) {
                btnLogin.isEnabled = true
                btnLogin.backgroundTintList =
                    ContextCompat.getColorStateList(requireContext(), R.color.primary)
            } else {
                btnLogin.isEnabled = false
                btnLogin.backgroundTintList =
                    ContextCompat.getColorStateList(requireContext(), R.color.primaryDisabled)
            }
        }
    }

    private fun onLoginClick() = with(binding) {
        btnLogin.setOnClickListener {
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()
            val isRemembered = cbRememberMe.isChecked

            val requestLoginDto = RequestLoginDto(email = email, password = password)
            viewModel.login(
                user = requestLoginDto, isRemembered = isRemembered, email = email
            )

        }
    }

    private fun clearInputs() = with(binding) {
        etEmail.text?.clear()
        etPassword.text?.clear()
    }
}
