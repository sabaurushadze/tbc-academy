package com.example.academy_tbc.screen.outfit_shop.outfits

import androidx.annotation.DrawableRes
import java.util.UUID

data class OutfitItem(
    val id: UUID,
    @param:DrawableRes val image: Int,
    val title: String,
    val price: Int,
    val category: String
)