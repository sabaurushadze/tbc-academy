package com.example.academy_tbc.data.mapper.home.location

import com.example.academy_tbc.data.model.response.home.location.LocationResponseDto
import com.example.academy_tbc.domain.model.home.location.Location

fun LocationResponseDto.toDomain(): Location {
    return Location(
        title = title,
        cover = cover
    )
}