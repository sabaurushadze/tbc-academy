package com.example.academy_tbc.presentation.screen.home.categories.mapper

import com.example.academy_tbc.domain.model.home.categories.Category
import com.example.academy_tbc.domain.model.home.categories.CategoryType
import com.example.academy_tbc.presentation.screen.core.toUiIconRes
import com.example.academy_tbc.presentation.screen.home.categories.model.CategoryUi

fun Category.toCategoryUi() =
    CategoryUi(
        id = id,
        title = title,
        icon = CategoryType.fromInt(id).toUiIconRes(),
        totalEvents = totalEvents
    )