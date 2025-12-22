package com.example.academy_tbc.presentation.screen.home

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.academy_tbc.databinding.FragmentHomeBinding
import com.example.academy_tbc.domain.model.home.categories.CategoryType
import com.example.academy_tbc.presentation.common.view.BaseFragment
import com.example.academy_tbc.presentation.extension.dpToPx
import com.example.academy_tbc.presentation.extension.lifecycleCollect
import com.example.academy_tbc.presentation.extension.lifecycleCollectLatest
import com.example.academy_tbc.presentation.extension.showSnackBar
import com.example.academy_tbc.presentation.screen.home.categories.adapter.CategoryAdapter
import com.example.academy_tbc.presentation.screen.home.categories.adapter.GridSpacingItemDecoration
import com.example.academy_tbc.presentation.screen.home.categories.mapper.getCategoryIconRes
import com.example.academy_tbc.presentation.screen.home.categories.model.CategoryUi
import com.example.academy_tbc.presentation.screen.home.trending_events.adapter.HorizontalSpacingItemDecoration
import com.example.academy_tbc.presentation.screen.home.trending_events.adapter.TrendingEventAdapter
import com.example.academy_tbc.presentation.screen.home.trending_events.model.TrendingEventUi
import com.example.academy_tbc.presentation.screen.home.upcoming_events.adapter.UpcomingEventAdapter
import com.example.academy_tbc.presentation.screen.home.upcoming_events.model.UpcomingEventUi
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(
    FragmentHomeBinding::inflate
) {
    private val viewModel: HomeViewModel by viewModels()

    private val upcomingEventAdapter by lazy {
        UpcomingEventAdapter(
            onClick = {
            }
        )
    }

    private val categoryAdapter by lazy {
        CategoryAdapter(
            onClick = {}
        )
    }

    private val trendingEventAdapter by lazy {
        TrendingEventAdapter(
            onClick = {}
        )
    }

    override fun bind() {
        val upcomingEvents: List<UpcomingEventUi> = listOf(
            UpcomingEventUi(
                id = 1,
                title = "Team Building",
                description = "Stay connected with upcoming company events and activities.",
                eventTypeId = 1,
                startDateTime = "2025-12-21T18:00:00Z",
                endDateTime = "2025-12-21T20:00:00Z",
                location = "Hall A",
                capacity = 50,
                imageUrl = null,
                isActive = true
            ),
            UpcomingEventUi(
                id = 2,
                title = "Product Launch",
                description = "Stay connected with upcoming company events and activities.",
                eventTypeId = 1,
                startDateTime = "2025-12-22T15:30:00Z",
                endDateTime = "2025-12-22T17:00:00Z",
                location = "Room 101",
                capacity = 200,
                imageUrl = null,
                isActive = true
            ),
            UpcomingEventUi(
                id = 3,
                title = "Workshop",
                description = "Training workshop",
                eventTypeId = 2,
                startDateTime = "2025-12-25T09:00:00Z",
                endDateTime = "2025-12-25T12:00:00Z",
                location = "Conference Room",
                capacity = 30,
                imageUrl = null,
                isActive = true
            )
        )

        val categories: List<CategoryUi> = listOf(
            CategoryUi(
                id = 1,
                categoryType = CategoryType.TEAM_BUILDING,
                title = "Team Building",
                icon = getCategoryIconRes(CategoryType.TEAM_BUILDING),
                totalEvents = 5
            ),
            CategoryUi(
                id = 2,
                categoryType = CategoryType.SPORTS,
                title = "Sports",
                icon = getCategoryIconRes(CategoryType.SPORTS),
                totalEvents = 3
            ),
            CategoryUi(
                id = 3,
                categoryType = CategoryType.WORKSHOPS,
                title = "Workshops",
                icon = getCategoryIconRes(CategoryType.WORKSHOPS),
                totalEvents = 2
            ),
            CategoryUi(
                id = 4,
                categoryType = CategoryType.HAPPY_FRIDAYS,
                title = "Happy Fridays",
                icon = getCategoryIconRes(CategoryType.HAPPY_FRIDAYS),
                totalEvents = 4
            ),
            CategoryUi(
                id = 5,
                categoryType = CategoryType.CULTURAL,
                title = "Cultural",
                icon = getCategoryIconRes(CategoryType.CULTURAL),
                totalEvents = 1
            ),
            CategoryUi(
                id = 6,
                categoryType = CategoryType.WELLNESS,
                title = "Wellness",
                icon = getCategoryIconRes(CategoryType.WELLNESS),
                totalEvents = 6
            )
        )
        val trendingEvents: List<TrendingEventUi> = listOf(
            TrendingEventUi(
                id = 1,
                title = "Tech Talk: AI in Business",
                date = "2025-12-21T18:00:00Z",
                imageUrl = "https://raw.githubusercontent.com/sabaurushadze/api-images/refs/heads/main/gpu/4060/rtx_4060_8gb_1.png"
            ),
            TrendingEventUi(
                id = 2,
                title = "Tech Talk: AI in Business",
                date = "2025-12-21T18:00:00Z",
                imageUrl = ""
            ),
            TrendingEventUi(
                id = 3,
                title = "Tech Talk: AI in Business",
                date = "2025-12-21T18:00:00Z",
                imageUrl = ""
            )
        )
        setUpUpcomingEventAdapter()
        setUpCategoryAdapter()
        setUpUpTrendingEventAdapter()
        upcomingEventAdapter.submitList(upcomingEvents)
        categoryAdapter.submitList(categories)
        trendingEventAdapter.submitList(trendingEvents)
    }

    override fun listeners() {
        observeState()
        observeSideEffects()
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
            addItemDecoration(GridSpacingItemDecoration(
                spanCount = 3,
                spacing = 12.dpToPx(),
                includeEdge = true
            ))
        }
    }

    private fun setUpUpTrendingEventAdapter() = with(binding) {
        rvTrendingEvents.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false )
            adapter = trendingEventAdapter
            isNestedScrollingEnabled = false
            addItemDecoration(HorizontalSpacingItemDecoration(
                16.dpToPx(),
                addStartSpacing = false,
                addEndSpacing = false
            ))
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
        }
    }


}