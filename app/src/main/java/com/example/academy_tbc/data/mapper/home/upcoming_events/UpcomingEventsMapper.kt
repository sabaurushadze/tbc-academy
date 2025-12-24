package com.example.academy_tbc.data.mapper.home.upcoming_events

import com.example.academy_tbc.data.dto.response.home.events.AgendaDto
import com.example.academy_tbc.data.dto.response.home.events.FeaturedSpeakersDto
import com.example.academy_tbc.data.dto.response.home.events.EventsResponseDto
import com.example.academy_tbc.domain.model.home.upcoming_events.Agenda
import com.example.academy_tbc.domain.model.home.upcoming_events.FeaturedSpeaker
import com.example.academy_tbc.domain.model.home.upcoming_events.Event

fun EventsResponseDto.toDomain() =
    Event(
        id = id,
        title = title,
        description = description,
        categoryId = categoryId,
        categoryTitle = categoryTitle,
        startDateTime = startDateTime,
        endDateTime = endDateTime,
        registrationDeadline = registrationDeadline,
        location = location,
        currentCapacity = currentCapacity,
        maxCapacity = maxCapacity,
        availableSlots = availableSlots,
        eventStatus = eventStatus,
        imageUrl = imageUrl,
        isVisible = isVisible,
        agendas = agenda?.map { it.toDomain() } ?: listOf(),
        featuredSpeakers = speakers?.map { it.toDomain() } ?: listOf()
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
    photoUrl = photoUrl
)
fun List<EventsResponseDto>.toDomain() = map { it.toDomain() }