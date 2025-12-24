package com.example.academy_tbc.presentation.screen.home.upcoming_events.mapper

import com.example.academy_tbc.domain.model.home.upcoming_events.Event
import com.example.academy_tbc.presentation.screen.home.upcoming_events.model.UpcomingEventUi
import com.example.academy_tbc.presentation.util.DateTimeParser

fun Event.toUpcomingEventUi() =
    UpcomingEventUi(
        id = id,
        title = title,
        categoryTitle = categoryTitle,
        categoryId = categoryId,
        description = description,
        monthAbbreviation = DateTimeParser.getMonthAbbr(startDateTime),
        monthNumber = DateTimeParser.getDay(startDateTime).toString(),
        time = "${DateTimeParser.getTime(startDateTime)} - ${DateTimeParser.getTime(endDateTime)}",
        location = location,
        availableSlots = availableSlots.toString(),
        currentCapacity = currentCapacity.toString(),
    )