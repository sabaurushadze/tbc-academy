package com.example.academy_tbc.presentation.screen.category.mapper

import com.example.academy_tbc.domain.model.categories.Category
import com.example.academy_tbc.presentation.screen.category.model.CategoryUi

fun Category.toUi(): CategoryUi {
    return CategoryUi(
        id = id,
        category = category.toUiTextRes(),
        image = image,
    )
}