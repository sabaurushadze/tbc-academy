package com.example.academy_tbc.presentation.screen.home.mapper

import com.example.academy_tbc.domain.model.location.Location
import com.example.academy_tbc.presentation.screen.home.model.UiLocation

fun Location.toPresentation(): UiLocation {
    return UiLocation(
        location = location,
        altitudeM = altitudeM,
        title = title,
        image = image,
        stars = stars,
        price = price,
    )
}