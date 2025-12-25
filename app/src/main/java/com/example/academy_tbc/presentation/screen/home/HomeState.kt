package com.example.academy_tbc.presentation.screen.home

import com.example.academy_tbc.presentation.screen.home.categories.model.CategoryUi
import com.example.academy_tbc.presentation.screen.home.trending_events.model.TrendingEventUi
import com.example.academy_tbc.presentation.screen.home.upcoming_events.model.UpcomingEventUi

data class HomeState(
    val isLoading: Boolean = false,
    val categories: List<CategoryUi> = listOf(),
    val events: List<UpcomingEventUi> = listOf(),
    val trendingEvents: List<TrendingEventUi> = listOf(),
)