package com.example.academy_tbc.presentation.screen.home.mapper

import com.example.academy_tbc.domain.model.home.location.Location
import com.example.academy_tbc.presentation.screen.home.model.LocationUi

fun Location.toUi(): LocationUi {
    return LocationUi(
        title = title,
        cover = cover
    )
}