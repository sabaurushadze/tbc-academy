package com.example.academy_tbc.data.dto.response.home.events

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