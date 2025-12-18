package com.example.academy_tbc.domain.model.location

data class Location(
    val id: Int,
    val title: String,
    val description: String,
    val latitude: Double,
    val longitude: Double,
    val imageUrl: String
)