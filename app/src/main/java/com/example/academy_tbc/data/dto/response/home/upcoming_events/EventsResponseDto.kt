package com.example.academy_tbc.data.dto.response.home.upcoming_events

import kotlinx.serialization.Serializable

@Serializable
data class EventsResponseDto(
    val id: Int,
    val title: String,
    val description: String?,
    val eventTypeId: Int,
    val startDateTime: String,
    val endDateTime: String,
    val location: String,
    val capacity: Int,
    val imageUrl: String?,
    val isActive: Boolean?,
    val agendas: List<AgendaDto>,
    val featuredSpeakers: List<FeaturedSpeakersDto>
)

@Serializable
data class AgendaDto(
    val id: Int,
    val time: String,
    val title: String,
    val description: String
)

@Serializable
data class FeaturedSpeakersDto(
    val id: Int,
    val name: String,
    val role: String,
    val imageUrl: String
)