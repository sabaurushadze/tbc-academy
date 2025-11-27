package com.example.academy_tbc.presentation.screen.home

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.academy_tbc.databinding.FragmentHomeBinding
import com.example.academy_tbc.presentation.common.BaseFragment
import com.example.academy_tbc.presentation.extension.lifecycle.lifecycleCollect
import com.example.academy_tbc.presentation.extension.lifecycle.lifecycleCollectLatest
import com.example.academy_tbc.presentation.screen.home.adapter.UsersAdapter
import com.example.academy_tbc.presentation.screen.home.adapter.UsersLoadStateAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(
    FragmentHomeBinding::inflate
) {
    private val viewModel: HomeViewModel by viewModels()

    private val usersAdapter by lazy { UsersAdapter() }

    override fun bind() {
        binding.rvUsers.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvUsers.adapter = usersAdapter.withLoadStateFooter(
            footer = UsersLoadStateAdapter { usersAdapter.retry() })
    }

    override fun listeners() {
        observeUsersPaging()
        navigateToProfile()
        observeNetwork()
        observeLoadState()
    }


    private fun observeUsersPaging() = with(binding) {
        lifecycleCollectLatest(viewModel.usersPager) { pagingData ->
            usersAdapter.submitData(viewLifecycleOwner.lifecycle, pagingData)
        }
    }

    private fun observeLoadState() = with(binding) {
        lifecycleCollectLatest(usersAdapter.loadStateFlow) { loadStates ->
            progressBar.isVisible = loadStates.refresh is LoadState.Loading
        }
    }

    private fun observeNetwork() {
        lifecycleCollect(viewModel.isConnected) { isConnected ->
            binding.tvNoInternet.isVisible = !isConnected
            if (isConnected) {
                usersAdapter.retry()
            }
        }
    }

    private fun navigateToProfile() {
        binding.btnProfile.setOnClickListener {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToProfileFragment()
            )
        }
    }
}