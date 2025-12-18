package com.example.academy_tbc.presentation.screen.location

sealed class MapEvent {
    data object GetLocation : MapEvent()
    data object ShowAllMarkers : MapEvent()
}