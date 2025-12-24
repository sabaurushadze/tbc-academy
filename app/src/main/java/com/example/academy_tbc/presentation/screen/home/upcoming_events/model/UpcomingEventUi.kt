package com.example.academy_tbc.presentation.screen.home.upcoming_events.model

data class UpcomingEventUi(
    val id: Int,
    val title: String,
    val categoryTitle: String,
    val categoryId: Int,
    val description: String?,
    val monthAbbreviation: String,
    val monthNumber: String,
    val time: String,
    val location: String,
    val availableSlots: String,
    val currentCapacity: String,
)