package com.example.academy_tbc.presentation.screen.core

import com.example.academy_tbc.R
import com.example.academy_tbc.domain.model.home.categories.CategoryType

fun CategoryType.toUiIconRes(): Int = when (this) {
    CategoryType.TEAM_BUILDING -> R.drawable.ic_groups
    CategoryType.SPORTS -> R.drawable.ic_sport
    CategoryType.WORKSHOPS -> R.drawable.ic_workshop
    CategoryType.HAPPY_FRIDAYS -> R.drawable.ic_happy_fridays
    CategoryType.CULTURAL -> R.drawable.ic_cultural
    CategoryType.WELLNESS -> R.drawable.ic_wellness
}