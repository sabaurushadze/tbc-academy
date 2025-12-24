package com.example.academy_tbc.presentation.screen.events.event_details.mapper

import com.example.academy_tbc.domain.model.home.upcoming_events.Event
import com.example.academy_tbc.presentation.screen.events.event_details.model.EventDetailUi
import com.example.academy_tbc.presentation.util.DateTimeParser

fun Event.toEventDetailUi() =
    EventDetailUi(
        id = id,
        categoryTitle = categoryTitle,
        categoryId = categoryId,
        title = title,
        description = description,
        location = location,
        availableSlots = availableSlots.toString(),
        currentCapacity = currentCapacity.toString(),
        time = "${DateTimeParser.getTime(startDateTime)} - ${DateTimeParser.getTime(endDateTime)}",
        date = DateTimeParser.getFormattedDate(startDateTime),
        registrationClosingDate = DateTimeParser.getFormattedDateTime(startDateTime),
        imageUrl = imageUrl,
        agendas = agendas.map { it.toPresentation() },
        featuredSpeakers = featuredSpeakers.map { it.toPresentation() }
    )

