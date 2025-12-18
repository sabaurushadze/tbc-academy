package com.example.academy_tbc.data.local.room.location

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LocationEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val description: String,
    val latitude: Double,
    val longitude: Double,
    val imageUrl: String
)