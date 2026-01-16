package com.example.academy_tbc.presentation.screen.home.outfit.model

import androidx.annotation.StringRes
import com.example.academy_tbc.domain.model.outfit.OutfitCategory

data class UiOutfit(
    val category: OutfitCategory,
    val name: String,
    val image: String,
    val priceAmount: Double,
    @param:StringRes val currencyRes: Int,
)