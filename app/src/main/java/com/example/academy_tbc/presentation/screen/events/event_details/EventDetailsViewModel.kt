package com.example.academy_tbc.presentation.screen.events.event_details

import com.example.academy_tbc.domain.usecase.event.GetEventByIdUseCase
import com.example.academy_tbc.presentation.common.mapper.toGenericString
import com.example.academy_tbc.presentation.common.view.BaseViewModel
import com.example.academy_tbc.presentation.screen.events.event_details.mapper.toEventDetailUi
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EventDetailsViewModel @Inject constructor(
    private val getEventByIdUseCase: GetEventByIdUseCase,
) :
    BaseViewModel<EventDetailsState, EventDetailsSideEffect, EventDetailsEvent>(EventDetailsState()) {


    override fun onEvent(event: EventDetailsEvent) {
        when (event) {
            is EventDetailsEvent.GetEventById -> getEventById(event.id)
        }
    }

    private fun getEventById(id: Int) {
        launchResource(
            apiCall = getEventByIdUseCase(id),
            onLoading = {
                updateUiState { copy(isLoading = isLoading) }
            },
            onSuccess = { event ->
                updateUiState {
                    copy(
                        event = event.toEventDetailUi()
                    )
                }
            },
            onError = {
                emitSideEffect(EventDetailsSideEffect.ShowError(error = it.toGenericString()))
            }
        )
    }

}