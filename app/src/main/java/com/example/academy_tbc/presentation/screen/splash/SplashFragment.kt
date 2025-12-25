package com.example.academy_tbc.presentation.screen.splash

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.academy_tbc.databinding.FragmentSplashBinding
import com.example.academy_tbc.presentation.common.view.BaseFragment
import com.example.academy_tbc.presentation.extension.lifecycleCollect
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding>(
    FragmentSplashBinding::inflate
) {
    private val viewModel: SplashViewModel by viewModels()

    override fun listeners() {
        observeSideEffects()
    }

    private fun observeSideEffects() {
        lifecycleCollect(viewModel.sideEffect) { effect ->
            when (effect) {
                SplashSideEffect.NavigateToHome -> {
                    findNavController().navigate(
                        SplashFragmentDirections.actionSplashFragmentToHomeFragment()
                    )
                }

                SplashSideEffect.NavigateToSignIn -> {
                    findNavController().navigate(
                        SplashFragmentDirections.actionSplashFragmentToSignInFragment()
                    )
                }

            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.onEvent(SplashEvent.OnStartSplash)
    }


    override fun onPause() {
        super.onPause()
        viewModel.onEvent(SplashEvent.OnStopSplash)
    }
}