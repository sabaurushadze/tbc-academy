package com.example.academy_tbc.presentation.screen.category.model

import androidx.annotation.StringRes

data class CategoryUi(
    val id: Int,
    val category: Int,
    val selected: Boolean = false,
    @param:StringRes val categoryRes: Int,
)