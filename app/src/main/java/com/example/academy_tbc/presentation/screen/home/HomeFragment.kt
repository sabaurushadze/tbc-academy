package com.example.academy_tbc.presentation.screen.home

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.academy_tbc.databinding.FragmentHomeBinding
import com.example.academy_tbc.presentation.common.BaseFragment
import kotlinx.coroutines.launch


class HomeFragment : BaseFragment<FragmentHomeBinding>(
    FragmentHomeBinding::inflate
) {
    private val viewModel: HomeViewModel by viewModels()

    private val usersAdapter by lazy { UsersAdapter() }

    override fun bind() {
        binding.rvUsers.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.rvUsers.adapter = usersAdapter
        viewModel.onEvent(HomeEvent.GetUsers)
    }

    override fun listeners() {
        observeState()
        navigateToProfile()
        setupRetryClick()
    }

    private fun observeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    binding.apply {
                        renderState(state)
                    }
                }
            }
        }
    }

    private fun renderState(state: HomeState) = binding.apply {
        progressBar.isVisible = state.isLoading

        val hasError = state.error.isNotEmpty()
        groupError.isVisible = hasError
        tvErrorMessage.text = state.error
        if (usersAdapter.currentList != state.users) {
            usersAdapter.submitList(state.users)
        }
    }

    private fun navigateToProfile() {
        binding.btnProfile.setOnClickListener {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToProfileFragment()
            )
        }
    }

    private fun setupRetryClick() {
        binding.btnRetry.setOnClickListener {
            viewModel.onEvent(HomeEvent.GetUsers)
        }
    }
}
