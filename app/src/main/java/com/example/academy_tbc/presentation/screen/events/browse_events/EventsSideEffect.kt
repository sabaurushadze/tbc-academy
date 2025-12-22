package com.example.academy_tbc.presentation.screen.events.browse_events

import com.example.academy_tbc.presentation.util.GenericString

sealed interface EventsSideEffect {
    data class ShowError(val error: GenericString) : EventsSideEffect
}