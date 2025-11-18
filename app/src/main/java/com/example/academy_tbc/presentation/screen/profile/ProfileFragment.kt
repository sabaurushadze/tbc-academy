package com.example.academy_tbc.presentation.screen.profile

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.academy_tbc.AuthApplication
import com.example.academy_tbc.databinding.FragmentProfileBinding
import com.example.academy_tbc.presentation.common.BaseFragment
import com.example.academy_tbc.presentation.common.ViewModelFactory
import kotlinx.coroutines.launch


class ProfileFragment : BaseFragment<FragmentProfileBinding>(
    FragmentProfileBinding::inflate
) {
    private val viewModel: ProfileViewModel by viewModels {
        ViewModelFactory {
            val app = requireActivity().application as AuthApplication
            ProfileViewModel(app.container.userDataStore)
        }
    }

    override fun listeners() {
        logOut()
        observeState()
        observeSideEffect()
    }

    override fun bind() {
        viewModel.onEvent(ProfileEvent.GetUserEmail)
    }

    private fun observeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { uiState ->
                    binding.tvEmail.text = uiState.email
                }
            }
        }
    }

    private fun observeSideEffect() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.sideEffect.collect { effect ->
                    when (effect) {
                        ProfileSideEffect.NavigateToLogIn -> findNavController().navigate(
                            ProfileFragmentDirections.actionProfileFragmentToLogInFragment()
                        )
                    }
                }
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
}