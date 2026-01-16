package com.example.academy_tbc.data.remote.dto.response.category

import kotlinx.serialization.Serializable

@Serializable
data class CategoryResponseDto(
    val id: Int,
    val name: String,
)