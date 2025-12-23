package com.example.academy_tbc.presentation.screen.home.categories.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.academy_tbc.domain.model.home.categories.CategoryType

data class CategoryUi(
    val id: Int,
    @param:StringRes val title: Int,
    @param:DrawableRes val icon: Int,
    val totalEvents: Int,
)