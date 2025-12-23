package com.example.academy_tbc.presentation.screen.events.event_details


sealed class EventDetailsEvent {
    data class GetEventById(val id: Int) : EventDetailsEvent()
}