package com.example.academy_tbc.presentation.screen.home

import com.example.academy_tbc.presentation.screen.home.model.LocationUi
import com.example.academy_tbc.presentation.screen.home.model.PostUi

data class HomeState(
    val locations: List<LocationUi> = emptyList(),
    val posts: List<PostUi> = emptyList(),
    val isLoading: Boolean = false,
    val showRetryButton: Boolean = false
)