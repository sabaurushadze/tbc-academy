package com.example.academy_tbc.presentation.screen.home.categories.model

import androidx.annotation.DrawableRes

data class CategoryUi(
    val id: Int,
    val title: String,
    @param:DrawableRes val icon: Int,
    val totalEvents: Int,
)