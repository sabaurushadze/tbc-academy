package com.example.academy_tbc.presentation.screen.home

import com.example.academy_tbc.presentation.screen.events.browse_events.EventsEvent

sealed class HomeEvent {
    data object GetCategories : HomeEvent()
    data object GetEvents : HomeEvent()
}