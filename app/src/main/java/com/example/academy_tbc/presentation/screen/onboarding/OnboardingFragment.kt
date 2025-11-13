package com.example.academy_tbc.presentation.screen.onboarding

import androidx.navigation.fragment.findNavController
import com.example.academy_tbc.databinding.FragmentOnboardingBinding
import com.example.academy_tbc.presentation.common.BaseFragment


class OnboardingFragment : BaseFragment<FragmentOnboardingBinding>(
    FragmentOnboardingBinding::inflate
) {
    override fun listeners() {
        navigateToRegister()
        navigateToLogin()
    }

    private fun navigateToRegister() {
        binding.btnRegister.setOnClickListener {
            findNavController().navigate(
                OnboardingFragmentDirections.actionOnboardingFragmentToRegisterFragment()
            )
        }
    }

    private fun navigateToLogin() {
        binding.btnLogin.setOnClickListener {
            findNavController().navigate(
                OnboardingFragmentDirections.actionOnboardingFragmentToLogInFragment()
            )
        }
    }
}