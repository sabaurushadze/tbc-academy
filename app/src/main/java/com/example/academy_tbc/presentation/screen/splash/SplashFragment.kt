package com.example.academy_tbc.presentation.screen.splash

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.academy_tbc.databinding.FragmentSplashBinding
import com.example.academy_tbc.presentation.common.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding>(
    FragmentSplashBinding::inflate
) {
    private val viewModel: SplashViewModel by viewModels()

    override fun bind() {
        viewModel.onEvent(SplashEvent.OnStartSplash)
    }

    override fun listeners() {
        observeSideEffects()
    }

    private fun observeSideEffects() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.sideEffect.collect {
                    when (it) {
                        SplashSideEffect.NavigateToHome -> findNavController().navigate(
                            SplashFragmentDirections.actionSplashFragmentToHomeFragment()
                        )

                        SplashSideEffect.NavigateToOnboarding -> findNavController().navigate(
                            SplashFragmentDirections.actionSplashFragmentToOnboardingFragment()
                        )
                    }
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        viewModel.onEvent(SplashEvent.OnStopSplash)
    }
}