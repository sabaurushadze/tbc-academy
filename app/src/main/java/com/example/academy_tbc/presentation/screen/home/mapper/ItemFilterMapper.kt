package com.example.academy_tbc.presentation.screen.home.mapper

import com.example.academy_tbc.presentation.screen.home.model.CheckboxOption
import com.example.academy_tbc.presentation.screen.home.model.FilterUi
import com.example.academy_tbc.presentation.screen.home.model.RadioOption

fun getFiltersByCategory(category: Int): List<FilterUi> {
    return when (category) {

        // GPU
        1 -> listOf(
            FilterUi.CheckboxGroup(
                title = "Condition",
                options = listOf(
                    CheckboxOption("new", "New", false),
                    CheckboxOption("used", "Used", false)
                )
            ),
            FilterUi.CheckboxGroup(
                title = "VRAM Size",
                options = listOf(
                    CheckboxOption("4", "4 GB", false),
                    CheckboxOption("8", "8 GB", false),
                    CheckboxOption("16", "16 GB", false),
                    CheckboxOption("24", "24 GB", false)
                )
            ),
            FilterUi.PriceRange(null, null)
        )
        2 -> listOf(
            FilterUi.CheckboxGroup(
                title = "Condition",
                options = listOf(
                    CheckboxOption("new", "New", false),
                    CheckboxOption("used", "Used", false)
                )
            ),
            FilterUi.CheckboxGroup(
                title = "Cpu model",
                options = listOf(
                    CheckboxOption("i3", "Intel i3", false),
                    CheckboxOption("i5", "Intel i5", false),
                    CheckboxOption("i7", "Intel i7", false),
                    CheckboxOption("i9", "Intel i9", false)
                )
            ),
            FilterUi.PriceRange(null, null)
        )

        else -> emptyList()
    }
}