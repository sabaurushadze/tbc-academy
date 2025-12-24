package com.example.academy_tbc.presentation.screen.events.browse_events.events.mapper

import com.example.academy_tbc.domain.model.home.upcoming_events.Event
import com.example.academy_tbc.presentation.screen.events.browse_events.events.model.EventUi
import com.example.academy_tbc.presentation.util.DateTimeParser

fun Event.toEventUi() =
    EventUi(
        id = id,
        eventStatus = eventStatus,
        categoryTitle = categoryTitle,
        categoryId = categoryId,
        time = "${DateTimeParser.getTime(startDateTime)} - ${DateTimeParser.getTime(endDateTime)}",
        monthAbbreviation = DateTimeParser.getMonthAbbr(startDateTime),
        monthNumber = DateTimeParser.getDay(startDateTime).toString(),
        title = title,
        location = location,
        availableSlots = availableSlots.toString(),
        currentCapacity = currentCapacity.toString(),
        fullDate = startDateTime
    )