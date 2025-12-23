package com.example.academy_tbc.presentation.screen.events.event_details.mapper

import com.example.academy_tbc.domain.model.home.upcoming_events.Event
import com.example.academy_tbc.presentation.screen.events.event_details.model.EventDetailUi

fun Event.toEventDetailUi() =
    EventDetailUi(
        id = id,
        title = title,
        description = description,
        eventTypeId = eventTypeId,
        startDateTime = startDateTime,
        endDateTime = endDateTime,
        location = location,
        capacity = capacity,
        imageUrl = imageUrl,
        isActive = isActive,
        agendas = agendas?.map { it.toPresentation() },
        featuredSpeakers = featuredSpeakers?.map { it.toPresentation() }
    )

