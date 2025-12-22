package com.example.academy_tbc.presentation.screen.events

import com.example.academy_tbc.domain.usecase.auth.sign_in.ValidateEmailUseCase
import com.example.academy_tbc.domain.usecase.auth.sign_in.ValidatePasswordUseCase
import com.example.academy_tbc.presentation.common.view.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EventsViewModel @Inject constructor(
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
) : BaseViewModel<EventsState, EventsSideEffect, EventsEvent>(EventsState()) {


    override fun onEvent(event: EventsEvent) {
        when(event) {

            else -> {}
        }

    }

}