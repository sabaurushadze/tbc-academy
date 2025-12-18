package com.example.academy_tbc.data.remote.mapper.location

import com.example.academy_tbc.data.remote.model.response.location.LocationResponseDto
import com.example.academy_tbc.domain.model.location.Location

fun LocationResponseDto.toDomain(): Location {
    return Location(
        id = id,
        title = title,
        description = description,
        latitude = latitude,
        longitude = longitude,
        imageUrl = imageUrl
    )
}