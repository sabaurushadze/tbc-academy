package com.example.academy_tbc.presentation.screen.home.mapper

import com.example.academy_tbc.domain.model.stats.StatsResponse
import com.example.academy_tbc.presentation.screen.home.StatsUi

fun StatsResponse.toPresentation(): StatsUi {
    return StatsUi(
        location = location,
        altitudeM = altitudeM,
        title = title,
        image = image,
        stars = stars
    )
}