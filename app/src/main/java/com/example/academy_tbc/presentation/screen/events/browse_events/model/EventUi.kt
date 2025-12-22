package com.example.academy_tbc.presentation.screen.events.browse_events.model

import com.example.academy_tbc.domain.model.home.categories.CategoryType

data class EventUi (
    val id: Int,
    val title: String,
    val description: String?,
    val eventTypeId: CategoryType,
    val startDateTime: String,
    val endDateTime: String,
    val location: String,
    val capacity: Int,
    val imageUrl: String?,
    val isActive: Boolean?
)