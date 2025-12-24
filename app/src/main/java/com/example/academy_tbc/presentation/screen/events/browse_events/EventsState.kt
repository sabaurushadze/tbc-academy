package com.example.academy_tbc.presentation.screen.events.browse_events

import com.example.academy_tbc.presentation.screen.events.browse_events.categories.model.EventCategoryUi
import com.example.academy_tbc.presentation.screen.events.browse_events.events.model.EventUi

data class EventsState(
    val isLoading: Boolean = false,
    val eventCategories: List<EventCategoryUi> = listOf(),
    val allEvents: List<EventUi> = listOf(),
    val events: List<EventUi> = emptyList(),
    val selectedCategoryId: Int = -1,
    val searchQuery: String = "",
    val filterLocation: String = "",
    val filterDate: String = "",
    val filterOnlyAvailable: Boolean = false
)