package com.example.academy_tbc.presentation.screen.home

import com.example.academy_tbc.presentation.screen.home.model.UiLocation

data class HomeState(
    val locations: List<UiLocation> = listOf(),
    val isLoading: Boolean = false,
)