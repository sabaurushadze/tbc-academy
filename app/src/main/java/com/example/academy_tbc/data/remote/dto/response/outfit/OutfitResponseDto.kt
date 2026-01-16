package com.example.academy_tbc.data.remote.dto.response.outfit

import kotlinx.serialization.Serializable

@Serializable
data class OutfitResponseDto(
    val id: Int,
    val category: Int,
    val name: String,
    val price: PriceDto,
    val image: String,
) {
    @Serializable
    data class PriceDto(
        val amount: Double,
        val currency: String,
    )
}
