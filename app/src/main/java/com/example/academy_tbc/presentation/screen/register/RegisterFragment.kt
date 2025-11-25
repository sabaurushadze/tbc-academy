package com.example.academy_tbc.presentation.screen.register

import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.academy_tbc.R
import com.example.academy_tbc.common.toMessageRes
import com.example.academy_tbc.databinding.FragmentRegisterBinding
import com.example.academy_tbc.presentation.common.BaseFragment
import com.example.academy_tbc.presentation.extension.lifecycleCollect
import com.example.academy_tbc.presentation.extension.lifecycleCollectLatest
import com.example.academy_tbc.presentation.extension.setTextIfDifferent
import com.example.academy_tbc.presentation.extension.showSnackBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterBinding>(
    FragmentRegisterBinding::inflate
) {
    private val viewModel: RegisterViewModel by viewModels()

    override fun listeners() {
        observeState()
        observeSideEffects()
        setupRegisterClick()
        setupBackButton()
        setupInputs()
    }

    private fun observeSideEffects() {
        lifecycleCollectLatest(viewModel.sideEffect) { effect ->
            when (effect) {
                is RegisterSideEffect.NavigateToLogin -> {
                    setFragmentResult(REQ_KEY_EMAIL, bundleOf(BUNDLE_KEY_EMAIL to effect.email))
                    setFragmentResult(
                        REQ_KEY_PASSWORD, bundleOf(BUNDLE_KEY_PASSWORD to effect.password)
                    )
                    findNavController().navigate(
                        RegisterFragmentDirections.actionRegisterFragmentToLogInFragment()
                    )
                }

                is RegisterSideEffect.ShowError -> {
                    val messageRes = effect.exception.toMessageRes()
                    binding.root.showSnackBar(getString(messageRes))
                }
            }
        }
    }

    private fun observeState() = with(binding) {
        lifecycleCollect(viewModel.state) { state ->
            etEmail.setTextIfDifferent(state.email)
            etPassword.setTextIfDifferent(state.password)
            etRepeatPassword.setTextIfDifferent(state.repeatPassword)
            btnRegister.isEnabled = state.isRegisterEnabled && !state.isLoading
            progressBar.isVisible = state.isLoading
            btnRegister.backgroundTintList = ContextCompat.getColorStateList(
                requireContext(),
                if (btnRegister.isEnabled) R.color.primary else R.color.primaryDisabled
            )
        }
    }


    private fun setupInputs() = with(binding) {
        etEmail.doAfterTextChanged { viewModel.onEvent(RegisterEvent.EmailChanged(it.toString())) }
        etPassword.doAfterTextChanged { viewModel.onEvent(RegisterEvent.PasswordChanged(it.toString())) }
        etRepeatPassword.doAfterTextChanged {
            viewModel.onEvent(
                RegisterEvent.RepeatPasswordChanged(
                    it.toString()
                )
            )
        }
    }

    private fun setupRegisterClick() = with(binding) {
        btnRegister.setOnClickListener {
            val state = viewModel.state.value
            viewModel.onEvent(
                RegisterEvent.Register(
                    email = state.email, password = state.password
                )
            )
        }
    }

    private fun setupBackButton() {
        binding.ibBack.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

    companion object {
        const val REQ_KEY_EMAIL = "reqKeyEmail"
        const val REQ_KEY_PASSWORD = "reqKeyPassword"
        const val BUNDLE_KEY_EMAIL = "bundleKeyEmail"
        const val BUNDLE_KEY_PASSWORD = "bundleKeyPassword"
    }
}