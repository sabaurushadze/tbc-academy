package com.example.academy_tbc.data.mapper.home.upcoming_events

import com.example.academy_tbc.data.dto.response.home.upcoming_events.AgendaDto
import com.example.academy_tbc.data.dto.response.home.upcoming_events.FeaturedSpeakersDto
import com.example.academy_tbc.data.dto.response.home.upcoming_events.EventsResponseDto
import com.example.academy_tbc.domain.model.home.upcoming_events.Agenda
import com.example.academy_tbc.domain.model.home.upcoming_events.FeaturedSpeaker
import com.example.academy_tbc.domain.model.home.upcoming_events.Event

fun EventsResponseDto.toDomain() =
    Event(
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
        agendas = agendas.map { it.toDomain() },
        featuredSpeakers = featuredSpeakers.map { it.toDomain() }
    )

fun AgendaDto.toDomain() = Agenda(
    id = id,
    time = time,
    title = title,
    description = description
)

fun FeaturedSpeakersDto.toDomain() = FeaturedSpeaker(
    id = id,
    name = name,
    role = role,
    imageUrl = imageUrl
)