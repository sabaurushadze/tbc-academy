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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


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
                    homeErrorMessages(uiState)
                }
            }
        }
    }

    private fun homeErrorMessages(uiState: HomeUiState) = with(binding) {
        when (uiState) {
            is HomeUiState.Success -> {
                viewModel.resetState()
                binding.tvTotalUsers.text = uiState.users.toString()
                binding.progressBar.isVisible = false
            }

            is HomeUiState.Error -> {
                viewModel.resetState()
                binding.progressBar.isVisible = false
                when (uiState.message) {
                    HomeExceptionErrors.EXCEPTION_NETWORK -> {
                        root.showSnackBar(getString(R.string.no_internet_connection_please_try_again))
                    }

                    HomeExceptionErrors.EXCEPTION_CREDENTIALS -> {
                        root.showSnackBar(getString(R.string.invalid_credentials))
                    }

                    HomeExceptionErrors.EXCEPTION_USER_NOT_FOUND -> {
                        root.showSnackBar(getString(R.string.user_not_found))
                    }

                    HomeExceptionErrors.EXCEPTION_UNKNOWN -> {
                        root.showSnackBar(getString(R.string.something_went_wrong_please_try_again))
                    }
                }
                binding.root.showSnackBar(getString(R.string.error_fetching_users))
            }

            is HomeUiState.Loading -> {
                viewModel.resetState()
                binding.progressBar.isVisible = true
            }

            is HomeUiState.Idle -> {}
        }
    }

    private fun logOut() {
        binding.btnLogOut.setOnClickListener {
            LogOutDialogFragment(
                onLogoutConfirmed = {
                    lifecycleScope.launch {
                        viewModel.removeUserToken()
                        withContext(Dispatchers.Main) {
                            navigateToOnBoarding()
                        }
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
