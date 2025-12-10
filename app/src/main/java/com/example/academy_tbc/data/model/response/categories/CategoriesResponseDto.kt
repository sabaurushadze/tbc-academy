package com.example.academy_tbc.data.model.response.categories

import kotlinx.serialization.Serializable

@Serializable
data class CategoriesResponseDto(
    val id: Int,
    val category: Int,
    val image: String,
)