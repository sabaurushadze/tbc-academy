package com.example.academy_tbc.screen.outfit_shop.outfits

import java.util.UUID

data class OutfitItem(
    val id: UUID,
    val image: Int,
    val title: String,
    val price: Int,
)