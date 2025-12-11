package com.example.academy_tbc.presentation.screen.category.mapper

import com.example.academy_tbc.domain.model.categories.Category
import com.example.academy_tbc.domain.model.categories.PcPartCategory
import com.example.academy_tbc.presentation.screen.category.model.CategoryUi

fun Category.toUi(): CategoryUi {
    return CategoryUi(
        id = id,
        category = category,
        categoryRes = PcPartCategory.fromInt(category).toUiTextRes(),
        image = image,
    )
}