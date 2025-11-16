package com.example.academy_tbc.presentation.screen.profile

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.example.academy_tbc.AuthApplication
import com.example.academy_tbc.R
import com.example.academy_tbc.databinding.FragmentProfileBinding
import com.example.academy_tbc.presentation.common.BaseFragment
import com.example.academy_tbc.presentation.common.ViewModelFactory
import kotlinx.coroutines.launch


class ProfileFragment : BaseFragment<FragmentProfileBinding>(
    FragmentProfileBinding::inflate
) {
    private val viewModel: ProfileViewModel by viewModels {
        ViewModelFactory {
            val application = requireActivity().application as AuthApplication
            ProfileViewModel(
                userTokenRepository = application.container.userTokenRepository
            )
        }
    }


    override fun listeners() {
        logOut()
        observe()
    }

    private fun observe() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.profileUiState.collect { uiState ->
                    binding.tvEmail.text = uiState.email
                }
            }
        }
    }

    private fun logOut() {
        binding.btnLogOut.setOnClickListener {
            LogOutDialogFragment(
                onLogoutConfirmed = {
                    lifecycleScope.launch {
                        viewModel.removeUserToken()
                        navigateToOnBoarding()
                    }
                }).show(
                childFragmentManager, LogOutDialogFragment.TAG
            )
        }
    }

    private fun navigateToOnBoarding() {
        val directions = ProfileFragmentDirections.actionProfileFragmentToLogInFragment()
        val options = navOptions {
            popUpTo(R.id.nav_graph) {
                inclusive = true
            }
            launchSingleTop = true
        }
        findNavController().navigate(directions.actionId, null, options)
    }
}