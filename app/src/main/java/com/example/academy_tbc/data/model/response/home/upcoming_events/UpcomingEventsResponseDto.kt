package com.example.academy_tbc.data.model.response.home.upcoming_events

import kotlinx.serialization.Serializable

@Serializable
data class UpcomingEventsResponseDto(
    val id: Int,
    val title: String,
    val description: String?,
    val eventTypeId: Int,
    val startDateTime: String,
    val endDateTime: String,
    val location: String,
    val capacity: Int,
    val imageUrl: String?,
    val isActive: Boolean?
)