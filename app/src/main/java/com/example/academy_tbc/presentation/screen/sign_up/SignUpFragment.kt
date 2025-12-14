package com.example.academy_tbc.presentation.screen.sign_up

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.academy_tbc.databinding.FragmentSignUpBinding
import com.example.academy_tbc.presentation.common.view.BaseFragment
import com.example.academy_tbc.presentation.extension.lifecycleCollect
import com.example.academy_tbc.presentation.extension.showSnackBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : BaseFragment<FragmentSignUpBinding>(
    FragmentSignUpBinding::inflate
) {
    private val viewModel: SignUpViewModel by viewModels()

    override fun listeners() {
        observeSideEffects()
        signUpWithEmailAndPassword()
        observeState()
        goBackToSignIn()
    }

    private fun observeState() {
        lifecycleCollect(viewModel.state) { state ->
            binding.progressBar.isVisible = state.isLoading
        }
    }

    private fun observeSideEffects() {
        lifecycleCollect(viewModel.effect) { effect ->
            when (effect) {
                SignUpSideEffect.NavigateToHome -> findNavController().navigate(
                    SignUpFragmentDirections.actionSignUpFragmentToHomeFragment()
                )

                is SignUpSideEffect.ShowError -> binding.root.showSnackBar(effect.error.getString(requireContext()))
            }
        }
    }

    private fun signUpWithEmailAndPassword() = with(binding) {
        btnSignUp.setOnClickListener {
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()

            viewModel.onEvent(
                SignUpEvent.SignUpWithEmailAndPassword(
                    email = email, password = password
                )
            )
        }
    }

    private fun goBackToSignIn() {
        binding.btnBackToSignIn.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }


}