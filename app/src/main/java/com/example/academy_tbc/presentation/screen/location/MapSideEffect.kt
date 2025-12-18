package com.example.academy_tbc.presentation.screen.location

import com.example.academy_tbc.presentation.screen.location.model.LocationUi
import com.example.academy_tbc.presentation.util.GenericString

sealed interface MapSideEffect {
    data class ShowError(val error: GenericString) : MapSideEffect
    data class ZoomToBounds(val items: List<LocationUi>) : MapSideEffect
}