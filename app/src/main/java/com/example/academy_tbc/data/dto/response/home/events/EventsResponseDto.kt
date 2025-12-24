package com.example.academy_tbc.data.dto.response.home.events

import kotlinx.serialization.Serializable

@Serializable
data class EventsResponseDto(
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
    val agenda: List<AgendaDto>?,
    val speakers: List<FeaturedSpeakersDto>?
)