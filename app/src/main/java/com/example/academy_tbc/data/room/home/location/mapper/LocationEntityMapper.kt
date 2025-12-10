package com.example.academy_tbc.data.room.home.location.mapper

import com.example.academy_tbc.data.room.home.location.LocationEntity
import com.example.academy_tbc.domain.model.home.location.Location

fun LocationEntity.toDomain(): Location =
    Location(
        id = id,
        title = title,
        cover = cover
    )