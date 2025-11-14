package com.example.academy_tbc.presentation.screen.home

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.example.academy_tbc.R
import com.example.academy_tbc.databinding.FragmentHomeBinding
import com.example.academy_tbc.presentation.common.BaseFragment
import com.example.academy_tbc.presentation.extension.showSnackBar
import com.example.academy_tbc.presentation.screen.home.state.HomeError
import kotlinx.coroutines.launch


class HomeFragment : BaseFragment<FragmentHomeBinding>(
    FragmentHomeBinding::inflate
) {
    private val viewModel: HomeViewModel by viewModels { HomeViewModel.Factory }

    override fun listeners() {
        observeTotalUsers()
        getUsers()
        logOut()
    }

    private fun observeTotalUsers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.homeUiState.collect { uiState ->
                    uiState?.let { handleHomeState(uiState) }
                }
            }
        }
    }

    private fun handleHomeState(uiState: HomeUiState) = with(binding) {
        when (uiState) {
            is HomeUiState.Success -> handleSuccess(uiState)
            is HomeUiState.Error -> handleError(uiState)
            is HomeUiState.Loading -> showLoading()
            is HomeUiState.Idle -> {}
        }
    }

    private fun handleSuccess(uiState: HomeUiState.Success) = with(binding) {
        viewModel.resetState()
        binding.tvTotalUsers.text = uiState.users.toString()
        binding.progressBar.isVisible = false

    }

    private fun handleError(uiState: HomeUiState.Error) = with(binding) {
        viewModel.resetState()
        progressBar.isVisible = false

        val message = when (uiState.message) {
            HomeError.EXCEPTION_NETWORK -> getString(R.string.no_internet_connection_please_try_again)
            HomeError.EXCEPTION_USER_NOT_FOUND -> getString(R.string.user_not_found)
            HomeError.EXCEPTION_UNKNOWN -> getString(R.string.something_went_wrong_please_try_again)
        }
        root.showSnackBar(message)
    }


    private fun showLoading() {
        binding.progressBar.isVisible = true
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

    private fun getUsers() {
        binding.btnGetUsers.setOnClickListener {
            binding.tvTotalUsers.text = ""
            viewModel.getUsers()
        }
    }

    private fun navigateToOnBoarding() {
        val directions = HomeFragmentDirections.actionHomeFragmentToOnboardingFragment(true)
        val options = navOptions {
            popUpTo(R.id.nav_graph) {
                inclusive = true
            }
            launchSingleTop = true
        }
        findNavController().navigate(directions.actionId, null, options)
    }
}
