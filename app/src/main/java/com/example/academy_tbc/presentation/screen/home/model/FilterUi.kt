package com.example.academy_tbc.presentation.screen.home.model

sealed class FilterUi {

    data class CheckboxGroup(
        val title: String,
        val options: List<CheckboxOption>
    ) : FilterUi()

    data class PriceRange(
        val minPrice: String?,
        val maxPrice: String?
    ) : FilterUi()
}



