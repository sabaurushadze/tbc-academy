package com.example.academy_tbc.presentation.screen.events.event_details

import com.example.academy_tbc.presentation.util.GenericString

sealed interface EventDetailsSideEffect {
    data class ShowError(val error: GenericString) : EventDetailsSideEffect
}