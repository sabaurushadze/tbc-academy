package com.example.academy_tbc.data.mapper.home

import com.example.academy_tbc.data.model.response.home.LocationResponseDto
import com.example.academy_tbc.domain.model.location.Location

fun LocationResponseDto.toDomain(): Location {
    return Location(
        title = title,
        cover = cover
    )
}