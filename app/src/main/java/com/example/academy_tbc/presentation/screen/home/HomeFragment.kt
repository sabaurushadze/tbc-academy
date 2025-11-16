package com.example.academy_tbc.presentation.screen.home

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.academy_tbc.AuthApplication
import com.example.academy_tbc.R
import com.example.academy_tbc.databinding.FragmentHomeBinding
import com.example.academy_tbc.presentation.common.BaseFragment
import com.example.academy_tbc.presentation.common.ViewModelFactory
import com.example.academy_tbc.presentation.extension.showSnackBar
import kotlinx.coroutines.launch


class HomeFragment : BaseFragment<FragmentHomeBinding>(
    FragmentHomeBinding::inflate
) {
    private val viewModel: HomeViewModel by viewModels {
        ViewModelFactory {
            val application = requireActivity().application as AuthApplication
            HomeViewModel(
                networkUsersRepository = application.container.usersRepository,
            )
        }
    }

    private val usersAdapter by lazy { UsersAdapter() }

    override fun bind() {
        binding.rvUsers.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.rvUsers.adapter = usersAdapter
    }


    override fun listeners() {
        observeUsers()
        navigateToProfile()
    }

    private fun observeUsers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.homeUiState.collect { uiState ->
                    when (uiState) {
                        is HomeUiState.Success -> {
                            usersAdapter.submitList(uiState.users)
                            handleSuccess()
                        }

                        is HomeUiState.Error -> handleError(uiState.error)
                        HomeUiState.Loading -> showLoading()
                    }
                }
            }
        }
    }

    private fun handleSuccess() = with(binding) {
        binding.progressBar.isVisible = false
    }

    private fun handleError(error: HomeError) = with(binding) {
        progressBar.isVisible = false

        val message = when (error) {
            HomeError.EXCEPTION_NETWORK -> getString(R.string.no_internet_connection_please_try_again)
            HomeError.EXCEPTION_USER_NOT_FOUND -> getString(R.string.user_not_found)
            HomeError.EXCEPTION_UNKNOWN -> getString(R.string.something_went_wrong_please_try_again)
        }
        root.showSnackBar(message)
    }

    private fun showLoading() {
        binding.progressBar.isVisible = true
    }


    private fun navigateToProfile() {
        binding.btnProfile.setOnClickListener {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToProfileFragment()
            )
        }
    }
}
