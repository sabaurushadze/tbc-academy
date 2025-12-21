package com.example.academy_tbc.data.mapper.home.trending_events

import com.example.academy_tbc.data.model.response.home.upcoming_events.UpcomingEventsResponseDto
import com.example.academy_tbc.domain.model.home.trending_events.TrendingEvent

fun UpcomingEventsResponseDto.toDomain() =
    TrendingEvent(
        id = id,
        imageUrl = imageUrl,
        title = title,
        date = startDateTime
    )
