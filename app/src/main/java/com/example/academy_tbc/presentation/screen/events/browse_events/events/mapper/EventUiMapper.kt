package com.example.academy_tbc.presentation.screen.events.browse_events.events.mapper

import com.example.academy_tbc.presentation.screen.events.browse_events.events.model.EventUi
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime

fun EventUi.localDate(): LocalDate {
    return LocalDateTime.parse(fullDate).date
}