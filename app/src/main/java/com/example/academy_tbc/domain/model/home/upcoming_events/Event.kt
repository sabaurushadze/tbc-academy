package com.example.academy_tbc.domain.model.home.upcoming_events

data class Event(
    val id: Int,
    val title: String,
    val description: String?,
    val categoryId: Int,
    val categoryTitle: String,
    val startDateTime: String,
    val endDateTime: String,
    val registrationDeadline: String,
    val location: String,
    val currentCapacity: Int,
    val maxCapacity: Int,
    val availableSlots: Int,
    val eventStatus: String,
    val imageUrl: String?,
    val isVisible: Boolean,
    val agendas: List<Agenda>,
    val featuredSpeakers: List<FeaturedSpeaker>,
)