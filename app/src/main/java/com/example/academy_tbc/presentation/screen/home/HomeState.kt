package com.example.academy_tbc.presentation.screen.home

import com.example.academy_tbc.presentation.screen.home.category.model.UiCategory
import com.example.academy_tbc.presentation.screen.home.outfit.model.UiOutfit

data class HomeState(
    val categories: List<UiCategory> = listOf(),
    val outfits: List<UiOutfit> = listOf(),
    val isLoading: Boolean = false,
    val selectedCategoryId: Int = 1,
    val favoriteOutfits: List<Int> = listOf()
) {
    val isCategoryOrOutfitReady: Boolean
        get() = categories.isNotEmpty() || outfits.isNotEmpty()
}


