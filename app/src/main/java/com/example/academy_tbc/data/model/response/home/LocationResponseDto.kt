package com.example.academy_tbc.data.model.response.home

import kotlinx.serialization.Serializable

@Serializable
data class LocationResponseDto(
    val title: String,
    val cover: String
)