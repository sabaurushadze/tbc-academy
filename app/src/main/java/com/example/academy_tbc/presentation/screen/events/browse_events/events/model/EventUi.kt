package com.example.academy_tbc.presentation.screen.events.browse_events.events.model

data class EventUi (
    val id: Int,
    val eventStatus: String,
    val title: String,
    val categoryTitle: String,
    val categoryId: Int,
    val time: String,
    val location: String,
    val availableSlots: String,
    val currentCapacity: String,
    val monthAbbreviation: String,
    val monthNumber: String,
    val fullDate: String
)