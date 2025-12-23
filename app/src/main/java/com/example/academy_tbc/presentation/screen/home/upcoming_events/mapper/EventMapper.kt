package com.example.academy_tbc.presentation.screen.home.upcoming_events.mapper

import com.example.academy_tbc.domain.model.home.upcoming_events.Event
import com.example.academy_tbc.presentation.screen.home.upcoming_events.model.UpcomingEventUi

fun Event.toUpcomingEventUi() =
    UpcomingEventUi(
        id = id,
        title = title,
        description = description,
//        eventTypeId = eventTypeId,
        startDateTime = startDateTime,
        endDateTime = endDateTime,
        location = location,
        capacity = capacity,
//        imageUrl = imageUrl,
        isActive = isActive
    )