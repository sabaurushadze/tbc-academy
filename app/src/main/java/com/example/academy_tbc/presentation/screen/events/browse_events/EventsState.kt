package com.example.academy_tbc.presentation.screen.events.browse_events

import com.example.academy_tbc.R
import com.example.academy_tbc.domain.model.home.categories.CategoryType
import com.example.academy_tbc.presentation.screen.events.browse_events.model.EventCategoryUi

data class EventsState(
    val isLoading: Boolean = false,
    val eventCategories: List<EventCategoryUi> = listOf(
        EventCategoryUi(
            id = 1,
            categoryType = CategoryType.TEAM_BUILDING,
            title = R.string.team_building,
        ),
        EventCategoryUi(
            id = 2,
            categoryType = CategoryType.SPORTS,
            title = R.string.team_building,
        ),
        EventCategoryUi(
            id = 3,
            categoryType = CategoryType.WORKSHOPS,
            title = R.string.team_building,
        ),
        EventCategoryUi(
            id = 4,
            categoryType = CategoryType.HAPPY_FRIDAYS,
            title = R.string.team_building,
        ),
        EventCategoryUi(
            id = 5,
            categoryType = CategoryType.CULTURAL,
            title = R.string.team_building,
        ),
        EventCategoryUi(
            id = 6,
            categoryType = CategoryType.WELLNESS,
            title = R.string.team_building,
        )
    ),
    val eventCategory: Int = 1
)