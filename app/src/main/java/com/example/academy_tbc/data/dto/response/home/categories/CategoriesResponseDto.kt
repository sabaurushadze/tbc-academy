package com.example.academy_tbc.data.dto.response.home.categories

import kotlinx.serialization.Serializable

@Serializable
data class CategoriesResponseDto(
    val id: Int,
    val title: String,
    val totalEvents: Int,
)