package com.example.academy_tbc.presentation.screen.home.model

data class SelectedFilters(
    val minPrice: Float?,
    val maxPrice: Float?,
    val selectedOptions: Map<String, List<String>>
)