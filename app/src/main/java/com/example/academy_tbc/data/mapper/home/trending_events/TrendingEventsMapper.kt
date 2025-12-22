package com.example.academy_tbc.data.mapper.home.trending_events

import com.example.academy_tbc.data.dto.response.home.upcoming_events.EventsResponseDto
import com.example.academy_tbc.domain.model.home.trending_events.TrendingEvent

fun EventsResponseDto.toDomain() =
    TrendingEvent(
        id = id,
        imageUrl = imageUrl,
        title = title,
        date = startDateTime
    )
