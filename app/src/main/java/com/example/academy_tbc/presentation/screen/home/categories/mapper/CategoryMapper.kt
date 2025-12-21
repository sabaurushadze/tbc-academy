package com.example.academy_tbc.presentation.screen.home.categories.mapper

import com.example.academy_tbc.R
import com.example.academy_tbc.domain.model.home.categories.CategoryType

fun getCategoryNameRes(category: CategoryType): Int {
    return when(category) {
        CategoryType.TEAM_BUILDING -> R.string.team_building
        CategoryType.SPORTS -> R.string.sports
        CategoryType.WORKSHOPS -> R.string.workshops
        CategoryType.HAPPY_FRIDAYS -> R.string.happy_fridays
        CategoryType.CULTURAL -> R.string.cultural
        CategoryType.WELLNESS -> R.string.wellness
    }
}

fun getCategoryIconRes(category: CategoryType): Int {
    return when(category) {
        CategoryType.TEAM_BUILDING -> R.drawable.ic_groups
        CategoryType.SPORTS -> R.drawable.ic_sport
        CategoryType.WORKSHOPS -> R.drawable.ic_workshop
        CategoryType.HAPPY_FRIDAYS -> R.drawable.ic_happy_fridays
        CategoryType.CULTURAL -> R.drawable.ic_cultural
        CategoryType.WELLNESS -> R.drawable.ic_wellness
    }
}