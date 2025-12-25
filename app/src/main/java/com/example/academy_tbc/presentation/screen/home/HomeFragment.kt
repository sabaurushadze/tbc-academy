package com.example.academy_tbc.presentation.screen.home

import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.academy_tbc.databinding.FragmentHomeBinding
import com.example.academy_tbc.presentation.common.view.BaseFragment
import com.example.academy_tbc.presentation.extension.dpToPx
import com.example.academy_tbc.presentation.extension.lifecycleCollect
import com.example.academy_tbc.presentation.extension.lifecycleCollectLatest
import com.example.academy_tbc.presentation.extension.showSnackBar
import com.example.academy_tbc.presentation.screen.home.categories.adapter.CategoryAdapter
import com.example.academy_tbc.presentation.screen.home.categories.adapter.GridSpacingItemDecoration
import com.example.academy_tbc.presentation.screen.home.trending_events.adapter.HorizontalSpacingItemDecoration
import com.example.academy_tbc.presentation.screen.home.trending_events.adapter.TrendingEventAdapter
import com.example.academy_tbc.presentation.screen.home.upcoming_events.adapter.UpcomingEventAdapter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(
    FragmentHomeBinding::inflate
) {
    private val viewModel: HomeViewModel by viewModels()

    private val upcomingEventAdapter by lazy {
        UpcomingEventAdapter(
            onClick = { upcomingEventId ->
                setFragmentResult(
                    REQUEST_KEY_UPCOMING_EVENT_ID,
                    bundleOf(BUNDLE_KEY_UPCOMING_EVENT_ID to upcomingEventId)
                )
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToEventDetailsFragment())
            }
        )
    }

    private val categoryAdapter by lazy {
        CategoryAdapter(
            onClick = { categoryId ->
                findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToEventsFragment(
                        categoryId
                    )
                )
            }
        )
    }

    private val trendingEventAdapter by lazy {
        TrendingEventAdapter(
            onClick = { trendingEventId ->
                setFragmentResult(
                    "request_key_trending_event_id",
                    bundleOf("bundle_key_trending_event_id" to trendingEventId)
                )
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToEventDetailsFragment())
            }
        )
    }

    override fun bind() {
        setUpUpcomingEventAdapter()
        setUpCategoryAdapter()
        setUpUpTrendingEventAdapter()
    }

    override fun listeners() {
        observeState()
        observeSideEffects()
        viewAllEvents()
    }

    private fun setUpUpcomingEventAdapter() = with(binding) {
        rvUpcomingEvents.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = upcomingEventAdapter
            isNestedScrollingEnabled = false
        }
    }

    private fun setUpCategoryAdapter() = with(binding) {
        rvCategories.apply {
            layoutManager = GridLayoutManager(context, 3)
            adapter = categoryAdapter
            isNestedScrollingEnabled = false
            addItemDecoration(
                GridSpacingItemDecoration(
                    spanCount = 3,
                    spacing = 12.dpToPx(),
                    includeEdge = true
                )
            )
        }
    }

    private fun setUpUpTrendingEventAdapter() = with(binding) {
        rvTrendingEvents.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = trendingEventAdapter
            isNestedScrollingEnabled = false
            addItemDecoration(
                HorizontalSpacingItemDecoration(
                    16.dpToPx(),
                    addStartSpacing = false,
                    addEndSpacing = false
                )
            )
        }
    }

    private fun observeSideEffects() = with(binding) {
        lifecycleCollectLatest(viewModel.sideEffect) { effect ->
            when (effect) {
                is HomeSideEffect.ShowError ->
                    root.showSnackBar(effect.error.getString(requireContext()))


            }
        }
    }

    private fun observeState() {
        lifecycleCollect(viewModel.state) { state ->
            binding.progressBar.isVisible = state.isLoading
            upcomingEventAdapter.submitList(state.events)
            categoryAdapter.submitList(state.categories)
            trendingEventAdapter.submitList(state.trendingEvents)
        }
    }

    private fun viewAllEvents() {
        binding.tvBtnViewAll.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToEventsFragment())
        }
    }

    companion object {
        const val REQUEST_KEY_UPCOMING_EVENT_ID = "request_key_upcoming_event_id"
        const val BUNDLE_KEY_UPCOMING_EVENT_ID = "bundle_key_upcoming_event_id"
    }

}