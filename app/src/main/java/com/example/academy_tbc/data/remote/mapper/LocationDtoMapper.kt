package com.example.academy_tbc.data.remote.mapper

import com.example.academy_tbc.data.remote.dto.response.locations.LocationResponseDto
import com.example.academy_tbc.domain.model.location.Location

fun LocationResponseDto.toDomain(): Location {
    return Location(
        location = location,
        altitudeM = altitudeM.toString(),
        title = title,
        image = image,
        stars = stars,
        price = price
    )
}