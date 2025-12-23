package com.example.academy_tbc.presentation.screen.events.browse_events

import com.example.academy_tbc.R
import com.example.academy_tbc.domain.model.home.categories.CategoryType
import com.example.academy_tbc.presentation.screen.events.browse_events.categories.model.EventCategoryUi
import com.example.academy_tbc.presentation.screen.events.browse_events.events.model.EventUi

data class EventsState(
    val isLoading: Boolean = false,
    val eventCategories: List<EventCategoryUi> = listOf(
        EventCategoryUi(
            id = 1,
            title = R.string.team_building,
        ),
        EventCategoryUi(
            id = 2,
            title = R.string.team_building,
        ),
        EventCategoryUi(
            id = 3,
            title = R.string.team_building,
        ),
        EventCategoryUi(
            id = 4,
            title = R.string.team_building,
        ),
        EventCategoryUi(
            id = 5,
            title = R.string.team_building,
        ),
        EventCategoryUi(
            id = 6,
            title = R.string.team_building,
        )
    ),
    val events: List<EventUi> = listOf(),
    val eventCategory: Int = 1
)