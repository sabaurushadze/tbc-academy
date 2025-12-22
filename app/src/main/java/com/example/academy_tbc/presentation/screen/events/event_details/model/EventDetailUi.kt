package com.example.academy_tbc.presentation.screen.events.event_details.model

data class EventDetailUi(
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
    val agendas: List<AgendaUi>,
    val featuredSpeakers: List<FeaturedSpeakerUi>
)

data class AgendaUi(
    val id: Int,
    val time: String,
    val title: String,
    val description: String
)

data class FeaturedSpeakerUi(
    val id: Int,
    val imageUrl: String,
    val name: String,
    val role: String,
)