package com.example.challenge.presentation.screen.splash

import androidx.fragment.app.viewModels
import com.example.challenge.databinding.FragmentSplashBinding
import com.example.challenge.presentation.common.BaseFragment
import com.example.challenge.presentation.extension.lifecycleCollectLatest
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment :
    BaseFragment<FragmentSplashBinding>(
        FragmentSplashBinding::inflate
    ) {

    override fun listeners() {
        observeSideEffects()
    }

    private val viewModel: SplashViewModel by viewModels()


    private fun observeSideEffects() {
        lifecycleCollectLatest(viewModel.sideEffect) { effect ->
            when (effect) {
                SplashSideEffect.NavigateToConnections -> {
//                    findNavController().navigate(
//                        SplashFragmentDirections.actionSplashFragmentToFriendsFragment()
                }
                SplashSideEffect.NavigateToLogIn -> {
//                    findNavController().navigate(
//                        SplashFragmentDirections.actionSplashFragmentToLogInFragment()
                }
            }
        }
    }
}
