package com.example.academy_tbc.domain.model.stats


data class StatsResponse(
    val location: String,
    val altitudeM: Int,
    val title: String,
    val image: String,
    val stars: Int?
)