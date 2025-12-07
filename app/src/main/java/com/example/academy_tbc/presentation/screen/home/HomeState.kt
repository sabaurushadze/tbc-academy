package com.example.academy_tbc.presentation.screen.home

data class HomeState(
    val isLoading: Boolean = false,
    val pcParts: List<PcPartUi> = emptyList()
)