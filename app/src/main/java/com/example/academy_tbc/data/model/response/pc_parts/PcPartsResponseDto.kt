package com.example.academy_tbc.data.model.response.pc_parts

import kotlinx.serialization.Serializable

@Serializable
data class PcPartsResponseDto(
    val id: Int,
    val title: String,
    val condition: String,
    val price: Float,
    val discount: Int,
    val images: List<String>,
)