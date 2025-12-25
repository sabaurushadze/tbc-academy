package com.example.academy_tbc.presentation.screen.auth.sign_in

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.academy_tbc.databinding.FragmentSignInBinding
import com.example.academy_tbc.presentation.common.view.BaseFragment
import com.example.academy_tbc.presentation.extension.lifecycleCollectLatest
import com.example.academy_tbc.presentation.extension.showSnackBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInFragment : BaseFragment<FragmentSignInBinding>(
    FragmentSignInBinding::inflate
) {
    private val viewModel: SignInViewModel by viewModels()

    override fun bind() {
    }

    override fun listeners() {
        observeState()
        observeSideEffects()
        navigateToSignUp()
        signIn()
    }

    private fun observeSideEffects() = with(binding) {
        lifecycleCollectLatest(viewModel.sideEffect) { effect ->
            when (effect) {
                is SignInSideEffect.ShowError ->
                    root.showSnackBar(effect.error.getString(requireContext()))

                is SignInSideEffect.ShowEmailError -> {
                    etEmail.error = effect.error.getString(requireContext())
                }

                is SignInSideEffect.ShowPasswordError -> {
                    etPassword.error = effect.error.getString(requireContext())
                }

                SignInSideEffect.NavigateToHome -> {
                    findNavController().navigate(SignInFragmentDirections.actionSignInFragmentToHomeFragment())
                }
            }
        }
    }

    private fun observeState() {
        lifecycleCollectLatest(viewModel.state) { state ->
            binding.progressBar.isVisible = state.isLoading
        }
    }


    private fun signIn() = with(binding) {
        binding.btnSignIn.setOnClickListener {
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()
            val rememberMe = checkboxRememberMe.isChecked

            viewModel.onEvent(
                SignInEvent.SignIn(
                    email = email,
                    password = password,
                    rememberMe = rememberMe
                )
            )
        }
    }

    private fun navigateToSignUp() {
        binding.tvBtnSignUp.setOnClickListener {
            findNavController().navigate(
                SignInFragmentDirections.actionSignInFragmentToSignUpFragment()
            )
        }
    }
}