package com.example.academy_tbc.presentation.screen.events.event_details

import com.example.academy_tbc.presentation.common.view.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EventDetailsViewModel @Inject constructor() :
    BaseViewModel<EventDetailsState, EventDetailsSideEffect, EventDetailsEvent>(EventDetailsState()) {


    override fun onEvent(event: EventDetailsEvent) {

    }



}