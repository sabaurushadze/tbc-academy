package com.example.academy_tbc.presentation.screen.home

import android.util.Log.d
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.academy_tbc.databinding.FragmentHomeBinding
import com.example.academy_tbc.presentation.common.view.BaseFragment
import com.example.academy_tbc.presentation.extension.lifecycleCollect
import com.example.academy_tbc.presentation.extension.showSnackBar
import com.example.academy_tbc.presentation.screen.home.adapter.HomeFeedAdapter
import com.example.academy_tbc.presentation.screen.home.adapter.HomeFeedItem
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(
    FragmentHomeBinding::inflate
) {
    private val viewModel: HomeViewModel by viewModels()
    private val homeFeedAdapter by lazy { HomeFeedAdapter() }

    override fun bind() {
        setUpHomeFeedAdapter()
    }

    private fun setUpHomeFeedAdapter() {
        binding.rvHomeFeed.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = homeFeedAdapter
        }
    }

    override fun listeners() {
        observeState()
        observeSideEffects()
        retryCallingApi()
    }

    private fun retryCallingApi() {
        binding.btnRetry.setOnClickListener {
            viewModel.onEvent(HomeEvent.GetPosts)
            viewModel.onEvent(HomeEvent.GetLocations)
        }
    }

    private fun observeSideEffects() {
        lifecycleCollect(viewModel.effect) { effect ->
            when (effect) {
                is HomeSideEffect.ShowError -> {
                    binding.root.showSnackBar(getString(effect.error))
                }
            }
        }
    }

    private fun observeState() {
        lifecycleCollect(viewModel.state) { state ->
            d("asdd", "Locations size: ${state.locations.size}, Posts size: ${state.posts.size}")
            binding.progressBar.isVisible = state.isLoading
            binding.btnRetry.isVisible = state.showRetryButton
            val items = mutableListOf<HomeFeedItem>()

            if (state.locations.isNotEmpty()) {
                items.add(HomeFeedItem.Locations(state.locations))
            }

            state.posts.forEach { post ->
                items.add(HomeFeedItem.Post(post))
            }

            homeFeedAdapter.submitList(items)
        }
    }
}