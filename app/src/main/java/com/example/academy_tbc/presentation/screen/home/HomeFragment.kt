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
        observe()
        getUsers()
        logOut()
    }

    private fun observe() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.homeUiState.collect { uiState ->
                    when (uiState) {
                        is HomeUiState.Success -> {
                            binding.tvTotalUsers.text = uiState.total.toString()
                            binding.progressBar.isVisible = false
                        }

                        HomeUiState.Error -> {
                            binding.root.showSnackBar(getString(R.string.error_fetching_users))
                            binding.progressBar.isVisible = false
                        }

                        HomeUiState.Loading -> {
                            binding.progressBar.isVisible = true
                        }

                        HomeUiState.Idle -> {}
                    }
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
