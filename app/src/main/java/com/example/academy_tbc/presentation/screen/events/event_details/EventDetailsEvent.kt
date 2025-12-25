package com.example.academy_tbc.presentation.screen.events.event_details


sealed class EventDetailsEvent {
    data class GetEventById(val id: Int) : EventDetailsEvent()
    data class CheckRegisterEvent(val eventId: Int) : EventDetailsEvent()
    data class ToggleRegistration(val eventId: Int) : EventDetailsEvent()
}