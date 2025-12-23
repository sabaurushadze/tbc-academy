package com.example.academy_tbc.presentation.screen.events.event_details

import com.example.academy_tbc.R
import com.example.academy_tbc.domain.model.home.categories.CategoryType
import com.example.academy_tbc.presentation.screen.events.browse_events.categories.model.EventCategoryUi
import com.example.academy_tbc.presentation.screen.events.browse_events.events.model.EventUi
import com.example.academy_tbc.presentation.screen.events.event_details.model.EventDetailUi

data class EventDetailsState(
    val isLoading: Boolean = false,
    val event: EventDetailUi? = null
)