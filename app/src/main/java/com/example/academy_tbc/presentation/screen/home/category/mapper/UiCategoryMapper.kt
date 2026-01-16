package com.example.academy_tbc.presentation.screen.home.category.mapper

import com.example.academy_tbc.domain.model.category.Category
import com.example.academy_tbc.presentation.screen.home.category.model.UiCategory

fun Category.toPresentation(): UiCategory {
    return UiCategory(
        id = id,
        name = name
    )
}