package com.example.academy_tbc.data.dto.response.home.events

import kotlinx.serialization.Serializable

@Serializable
data class FeaturedSpeakersDto(
    val id: Int,
    val name: String,
    val role: String,
    val photoUrl: String
)