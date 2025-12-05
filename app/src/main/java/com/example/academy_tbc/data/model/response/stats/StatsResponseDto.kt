package com.example.academy_tbc.data.model.response.stats

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StatsResponseDto(
    val location: String,
    @SerialName("altitude_m") val altitudeM: Int,
    val title: String,
    val image: String,
    val stars: Int?
)