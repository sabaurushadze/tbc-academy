package com.example.academy_tbc.presentation.screen.home

import com.example.academy_tbc.presentation.screen.home.model.LocationUi

data class HomeState(
    val locations: List<LocationUi> = emptyList(),
    val isLoading: Boolean = false,
)