package com.example.academy_tbc.presentation.screen.events.browse_events


sealed class EventsEvent {
    data class SaveCategory(val eventCategory: Int) : EventsEvent()
}