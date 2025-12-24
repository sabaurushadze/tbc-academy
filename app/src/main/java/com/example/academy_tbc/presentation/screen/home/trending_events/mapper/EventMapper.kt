package com.example.academy_tbc.presentation.screen.home.trending_events.mapper

import com.example.academy_tbc.domain.model.home.upcoming_events.Event
import com.example.academy_tbc.presentation.screen.home.trending_events.model.TrendingEventUi
import com.example.academy_tbc.presentation.util.DateTimeParser

fun Event.toTrendingEventUi() =
    TrendingEventUi(
        id = id,
        title = title,
        date = DateTimeParser.getFormattedDate(startDateTime),
        imageUrl = imageUrl
    )