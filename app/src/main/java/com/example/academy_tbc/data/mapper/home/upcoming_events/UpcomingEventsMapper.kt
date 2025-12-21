package com.example.academy_tbc.data.mapper.home.upcoming_events

import com.example.academy_tbc.data.model.response.home.upcoming_events.UpcomingEventsResponseDto
import com.example.academy_tbc.domain.model.home.upcoming_events.UpcomingEvent

fun UpcomingEventsResponseDto.toDomain() =
    UpcomingEvent(
        id = id,
        title = title,
        description = description,
        eventTypeId = eventTypeId,
        startDateTime = startDateTime,
        endDateTime = endDateTime,
        location = location,
        capacity = capacity,
        imageUrl = imageUrl,
        isActive = isActive
    )