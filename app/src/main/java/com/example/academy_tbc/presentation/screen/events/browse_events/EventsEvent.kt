package com.example.academy_tbc.presentation.screen.events.browse_events


sealed class EventsEvent {
    data class SaveCategory(val eventCategory: Int) : EventsEvent()
    data class Search(val query: String) : EventsEvent()
    data class ApplyFilters(val location: String, val date: String, val onlyAvailable: Boolean) : EventsEvent()
    data object GetCategories : EventsEvent()
    data object GetEvents : EventsEvent()

}