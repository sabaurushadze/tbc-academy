package com.example.academy_tbc.presentation.screen.home.upcoming_events.model

data class UpcomingEventUi(
    val id: Int,
    val title: String,
    val description: String?,
//    val eventTypeId: Int,
    val startDateTime: String,
    val endDateTime: String,
    val location: String,
    val capacity: Int,
//    val imageUrl: String?,
    val isActive: Boolean?
)