package com.example.academy_tbc.presentation.screen.home

import com.example.academy_tbc.presentation.screen.category.model.CategoryUi
import com.example.academy_tbc.presentation.screen.home.model.PcPartUi
import com.example.academy_tbc.presentation.screen.home.model.PcPartsQueryUi

data class HomeState(
    val isLoading: Boolean = false,
    val pcParts: List<PcPartUi> = emptyList(),
    val categories: List<CategoryUi> = listOf(),
    val category: Int = 1,
    val query: PcPartsQueryUi = PcPartsQueryUi(),
)