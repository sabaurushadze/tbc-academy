package com.example.academy_tbc.presentation.screen.events.event_details

import com.example.academy_tbc.presentation.screen.events.event_details.model.EventDetailUi

data class EventDetailsState(
    val isLoading: Boolean = false,
    val event: EventDetailUi? = null,
    val isRegistered: Boolean = false
)