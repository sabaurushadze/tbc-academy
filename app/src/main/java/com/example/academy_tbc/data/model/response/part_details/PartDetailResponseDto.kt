package com.example.academy_tbc.data.model.response.part_details

import kotlinx.serialization.Serializable

@Serializable
data class PartDetailResponseDto(
    val id: Int,
    val title: String,
    val category: Int,
    val condition: String,
    val price: Float,
    val discount: Int,
    val images: List<String>,
    val description: String,
    val brand: String,
    val warranty: Int,
    val model: String,
    val memorySize: String
)