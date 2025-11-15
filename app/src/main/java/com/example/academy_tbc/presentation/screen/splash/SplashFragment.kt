package com.example.academy_tbc.presentation.screen.splash

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.example.academy_tbc.AuthApplication
import com.example.academy_tbc.R
import com.example.academy_tbc.databinding.FragmentSplashBinding
import com.example.academy_tbc.presentation.common.BaseFragment
import com.example.academy_tbc.presentation.common.ViewModelFactory
import kotlinx.coroutines.launch

class SplashFragment : BaseFragment<FragmentSplashBinding>(
    FragmentSplashBinding::inflate
) {
    private val viewModel: SplashViewModel by viewModels {
        ViewModelFactory {
            val application = requireActivity().application as AuthApplication
            SplashViewModel(
                userTokenRepository = application.container.userTokenRepository
            )
        }
    }

    override fun listeners() {
        observe()
    }

    private fun observe() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    uiState.isLoggedIn?.let { isLoggedIn ->
                        if (isLoggedIn) navigateToHome()
                        else navigateToOnboarding()
                    }
                }
            }
        }
    }

    private fun navigateToOnboarding() {
        val directions = SplashFragmentDirections.actionSplashFragmentToOnboardingFragment()
        val options = navOptions {
            popUpTo(R.id.splashFragment) {
                inclusive = true
            }
            launchSingleTop = true
        }
        findNavController().navigate(directions.actionId, null, options)
    }

    private fun navigateToHome() {
        val directions = SplashFragmentDirections.actionSplashFragmentToHomeFragment()
        val options = navOptions {
            popUpTo(R.id.splashFragment) {
                inclusive = true
            }
            launchSingleTop = true
        }
        findNavController().navigate(directions.actionId, null, options)
    }

}