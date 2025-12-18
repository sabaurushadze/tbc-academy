package com.example.academy_tbc.data.local.room.mapper

import com.example.academy_tbc.data.local.room.location.LocationEntity
import com.example.academy_tbc.domain.model.location.Location

fun LocationEntity.toDomain() = Location(
    id = id,
    title = title,
    description = description,
    latitude = latitude,
    longitude = longitude,
    imageUrl = imageUrl
)