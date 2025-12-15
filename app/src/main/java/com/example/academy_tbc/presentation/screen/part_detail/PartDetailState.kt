package com.example.academy_tbc.presentation.screen.part_detail

import com.example.academy_tbc.presentation.screen.part_detail.model.PartDetailUi

data class PartDetailState(
    val isLoading: Boolean = false,
    val partDetails: PartDetailUi? = null,
)