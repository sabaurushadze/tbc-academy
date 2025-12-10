package com.example.academy_tbc.presentation.screen.home

import com.example.academy_tbc.presentation.screen.home.model.PcPartUi

data class HomeState(
    val isLoading: Boolean = false,
    val pcParts: List<PcPartUi> = emptyList()
)