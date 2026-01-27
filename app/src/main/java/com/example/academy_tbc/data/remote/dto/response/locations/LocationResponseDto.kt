package com.example.academy_tbc.data.remote.dto.response.locations

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LocationResponseDto(
    val location: String,
    @SerialName("altitude_m")val altitudeM: Int,
    val title: String,
    val image: String,
    val stars: Int,
    val price: String
)
