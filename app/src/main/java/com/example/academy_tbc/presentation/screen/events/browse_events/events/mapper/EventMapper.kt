package com.example.academy_tbc.presentation.screen.events.browse_events.events.mapper

import com.example.academy_tbc.domain.model.home.upcoming_events.Event
import com.example.academy_tbc.presentation.screen.events.browse_events.events.model.EventUi

fun Event.toEventUi() =
    EventUi(
        id = id,
        title = title,
//        description = description,
        eventTypeId = eventTypeId,
        startDateTime = startDateTime,
        endDateTime = endDateTime,
        location = location,
        capacity = capacity,
//        imageUrl = imageUrl,
        isActive = isActive
    )