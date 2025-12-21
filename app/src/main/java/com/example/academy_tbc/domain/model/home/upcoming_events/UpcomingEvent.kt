package com.example.academy_tbc.domain.model.home.upcoming_events

import kotlinx.serialization.Serializable

data class UpcomingEvent(
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