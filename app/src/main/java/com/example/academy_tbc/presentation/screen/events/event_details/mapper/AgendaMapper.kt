package com.example.academy_tbc.presentation.screen.events.event_details.mapper

import com.example.academy_tbc.domain.model.home.upcoming_events.Agenda
import com.example.academy_tbc.presentation.screen.events.event_details.model.AgendaUi

fun Agenda.toPresentation() = AgendaUi(
    id = id,
    time = time,
    title = title,
    description = description
)