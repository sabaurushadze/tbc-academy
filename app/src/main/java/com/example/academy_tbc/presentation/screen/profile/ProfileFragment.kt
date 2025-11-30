package com.example.academy_tbc.presentation.screen.profile

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.academy_tbc.databinding.FragmentProfileBinding
import com.example.academy_tbc.presentation.common.view.BaseFragment
import com.example.academy_tbc.presentation.extension.lifecycle.lifecycleCollect
import com.example.academy_tbc.presentation.extension.lifecycle.lifecycleCollectLatest
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(
    FragmentProfileBinding::inflate
) {
    private val viewModel: ProfileViewModel by viewModels()

    override fun listeners() {
        logOut()
        observeState()
        observeSideEffect()
        navigateBackToHome()
    }

    override fun bind() {
        viewModel.onEvent(ProfileEvent.GetUserEmail)
    }

    private fun observeState() {
        lifecycleCollect(viewModel.state) { state ->
            binding.tvEmail.text = state.email
        }
    }

    private fun observeSideEffect() {
        lifecycleCollectLatest(viewModel.sideEffect) { effect ->
            when (effect) {
                ProfileSideEffect.NavigateToLogIn -> findNavController().navigate(
                    ProfileFragmentDirections.actionProfileFragmentToLogInFragment()
                )
            }
        }
    }

    private fun logOut() {
        binding.btnLogOut.setOnClickListener {
            LogOutDialogFragment(
                onLogoutConfirmed = {
                    viewModel.onEvent(ProfileEvent.RemoveUserToken)
                }).show(
                childFragmentManager, LogOutDialogFragment.TAG
            )
        }
    }

    private fun navigateBackToHome() {
        binding.ibBack.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }
}