package com.example.academy_tbc.presentation.screen.home.model

import androidx.annotation.StringRes

sealed class FilterUi {
    data class CheckboxGroup(
        @param:StringRes val titleRes: Int? = null,
        val filterKey: String,
        val options: List<CheckboxOption>
    ) : FilterUi()

    data class PriceRange(
        var minPrice: String?,
        var maxPrice: String?
    ) : FilterUi()
}



