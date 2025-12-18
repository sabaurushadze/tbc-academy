package com.example.academy_tbc.data.local.room.mapper

import com.example.academy_tbc.data.local.room.location.LocationEntity
import com.example.academy_tbc.data.remote.model.response.location.LocationResponseDto

fun LocationResponseDto.toEntity() = LocationEntity(
    id = id,
    title = title,
    description = description,
    latitude = latitude,
    longitude = longitude,
    imageUrl = imageUrl
)