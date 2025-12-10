package com.example.academy_tbc.data.room.home.location

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LocationEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val title: String,
    val cover: String,
)

