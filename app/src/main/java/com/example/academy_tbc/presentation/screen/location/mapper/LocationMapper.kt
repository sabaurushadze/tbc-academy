package com.example.academy_tbc.presentation.screen.location.mapper

import com.example.academy_tbc.domain.model.location.Location
import com.example.academy_tbc.presentation.screen.location.model.LocationUi

fun Location.toPresentation(): LocationUi {
    return LocationUi(
        id = id,
        locationTitle = title,
        locationDescription = description,
        latitude = latitude,
        longitude = longitude,
        imageUrl = imageUrl
    )
}

fun List<Location>.toPresentation(): List<LocationUi> {
    return this.map { location ->
        location.toPresentation()
    }
}