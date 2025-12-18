package com.example.academy_tbc.presentation.screen.location

import com.example.academy_tbc.presentation.screen.location.model.LocationUi

data class MapState(
    val isLoading: Boolean = false,
    val locations: List<LocationUi> = emptyList(),
    val hasLocationPermission: Boolean = false,
    val hasLocationEnabled: Boolean = false
)