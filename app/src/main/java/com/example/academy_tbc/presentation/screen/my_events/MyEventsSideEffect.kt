package com.example.academy_tbc.presentation.screen.my_events

import com.example.academy_tbc.presentation.util.GenericString

sealed interface MyEventsSideEffect {
    data class ShowError(val error: GenericString) : MyEventsSideEffect
}