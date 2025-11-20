package com.example.academy_tbc.presentation.screen.login

import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.academy_tbc.R
import com.example.academy_tbc.databinding.FragmentLogInBinding
import com.example.academy_tbc.presentation.common.BaseFragment
import com.example.academy_tbc.presentation.extension.setTextIfDifferent
import com.example.academy_tbc.presentation.extension.showSnackBar
import com.example.academy_tbc.presentation.screen.register.RegisterFragment.Companion.BUNDLE_KEY_EMAIL
import com.example.academy_tbc.presentation.screen.register.RegisterFragment.Companion.BUNDLE_KEY_PASSWORD
import com.example.academy_tbc.presentation.screen.register.RegisterFragment.Companion.REQ_KEY_EMAIL
import com.example.academy_tbc.presentation.screen.register.RegisterFragment.Companion.REQ_KEY_PASSWORD
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LogInFragment : BaseFragment<FragmentLogInBinding>(
    FragmentLogInBinding::inflate
) {
    private val viewModel: LogInViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFragmentResultListener(REQ_KEY_EMAIL) { requestKey, bundle ->
            bundle.getString(BUNDLE_KEY_EMAIL)
                ?.let { viewModel.onEvent(LogInEvent.EmailChanged(it)) }
        }

        setFragmentResultListener(REQ_KEY_PASSWORD) { requestKey, bundle ->
            bundle.getString(BUNDLE_KEY_PASSWORD)
                ?.let { viewModel.onEvent(LogInEvent.PasswordChanged(it)) }
        }
    }

    override fun listeners() {
        observeState()
        observeSideEffects()
        setupLoginClick()
        setupInputs()
    }

    private fun observeSideEffects() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.sideEffect.collect { effect ->
                    when (effect) {
                        LogInSideEffect.NavigateToHome -> {
                            findNavController().navigate(
                                LogInFragmentDirections.actionLogInFragmentToHomeFragment()
                            )
                        }

                        is LogInSideEffect.ShowError -> binding.root.showSnackBar(effect.message)
                    }
                }
            }
        }
    }

    private fun observeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    binding.apply {
                        etEmail.setTextIfDifferent(state.email)
                        etPassword.setTextIfDifferent(state.password)
                        cbRememberMe.isChecked = state.isRemembered
                        btnLogin.isEnabled = state.isLoginEnabled && !state.isLoading
                        progressBar.isVisible = state.isLoading
                        btnLogin.backgroundTintList = ContextCompat.getColorStateList(
                            requireContext(),
                            if (btnLogin.isEnabled) R.color.primary else R.color.primaryDisabled
                        )
                    }
                }
            }
        }
    }

    private fun setupInputs() = with(binding) {
        etEmail.doAfterTextChanged { viewModel.onEvent(LogInEvent.EmailChanged(it.toString())) }
        etPassword.doAfterTextChanged { viewModel.onEvent(LogInEvent.PasswordChanged(it.toString())) }
        cbRememberMe.setOnCheckedChangeListener { _, isChecked ->
            viewModel.onEvent(LogInEvent.RememberMeChanged(isChecked))
        }
    }

    private fun setupLoginClick() = with(binding) {
        btnLogin.setOnClickListener {
            val state = viewModel.state.value
            viewModel.onEvent(
                LogInEvent.LogIn(
                    email = state.email,
                    password = state.password,
                    isRemembered = state.isRemembered
                )
            )
        }
    }
}
