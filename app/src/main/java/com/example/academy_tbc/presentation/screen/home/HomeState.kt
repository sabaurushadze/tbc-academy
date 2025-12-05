package com.example.academy_tbc.presentation.screen.home

data class HomeState(
    val stats: List<StatsUi>? = null,
    val isLoading: Boolean = false,
)