package com.example.academy_tbc.presentation.screen.category.model

import androidx.annotation.StringRes

data class CategoryUi(
    val id: Int,
    val image: String,
    val category: Int,
    @param:StringRes val categoryRes: Int,
)