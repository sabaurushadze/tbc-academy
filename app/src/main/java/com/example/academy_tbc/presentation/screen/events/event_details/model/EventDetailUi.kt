package com.example.academy_tbc.presentation.screen.events.event_details.model

data class EventDetailUi(
    val id: Int,
    val categoryTitle: String,
    val categoryId: Int,
    val title: String,
    val description: String?,
    val location: String,
    val date: String,
    val time: String,
    val availableSlots: String,
    val currentCapacity: String,
    val registrationClosingDate: String,
    val imageUrl: String?,
    val agendas: List<AgendaUi>?,
    val featuredSpeakers: List<FeaturedSpeakerUi>?
)

data class AgendaUi(
    val id: Int,
    val time: String,
    val title: String,
    val description: String?
)

data class FeaturedSpeakerUi(
    val id: Int,
    val photoUrl: String,
    val name: String,
    val role: String,
)