package com.example.academy_tbc.presentation.screen.events.browse_events

import com.example.academy_tbc.presentation.common.view.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EventsViewModel @Inject constructor() :
    BaseViewModel<EventsState, EventsSideEffect, EventsEvent>(EventsState()) {


    override fun onEvent(event: EventsEvent) {
        when (event) {
            is EventsEvent.SaveCategory -> saveCategory(event.eventCategory)
        }

    }



    private fun saveCategory(categoryEventId: Int) {
        updateUiState {
            val updatedCategories = eventCategories.map {
                it.copy(selected = it.id == categoryEventId)
            }
            copy(
                eventCategory = categoryEventId,
                eventCategories = updatedCategories
            )
        }
    }

}