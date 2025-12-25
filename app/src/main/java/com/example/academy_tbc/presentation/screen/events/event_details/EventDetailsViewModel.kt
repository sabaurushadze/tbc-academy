package com.example.academy_tbc.presentation.screen.events.event_details

import com.example.academy_tbc.domain.usecase.event.GetEventByIdUseCase
import com.example.academy_tbc.domain.usecase.event_registration.CheckEventUseCase
import com.example.academy_tbc.domain.usecase.event_registration.RegisterEventUseCase
import com.example.academy_tbc.domain.usecase.event_registration.UnregisterEventUseCase
import com.example.academy_tbc.presentation.common.mapper.toGenericString
import com.example.academy_tbc.presentation.common.view.BaseViewModel
import com.example.academy_tbc.presentation.screen.events.event_details.mapper.toEventDetailUi
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EventDetailsViewModel @Inject constructor(
    private val getEventByIdUseCase: GetEventByIdUseCase,

    private val checkEventUseCase: CheckEventUseCase,
    private val registerEventUseCase: RegisterEventUseCase,
    private val unregisterEventUseCase: UnregisterEventUseCase
) :
    BaseViewModel<EventDetailsState, EventDetailsSideEffect, EventDetailsEvent>(EventDetailsState()) {

    override fun onEvent(event: EventDetailsEvent) {
        when (event) {
            is EventDetailsEvent.GetEventById -> getEventById(event.id)
            is EventDetailsEvent.CheckRegisterEvent ->isAlreadyRegistered(event.eventId)
            is EventDetailsEvent.ToggleRegistration -> toggleRegistration(event.eventId)
        }
    }

    private fun toggleRegistration(eventId: Int) {
        val currentlyRegistered = state.value.isRegistered
        if (currentlyRegistered) {
            unregisterEvent(eventId)
        } else {
            registerEvent(eventId)
        }
    }

    private fun isAlreadyRegistered(eventId: Int) {
        launchResource(
            apiCall = checkEventUseCase(eventId),
            onLoading = {
                updateUiState { copy(isLoading = isLoading) }
            },
            onSuccess = {
                updateUiState {
                    copy(
                        isRegistered = it.isRegistered
                    )
                }
            },
            onError = {}
        )
    }

    private fun unregisterEvent(eventId: Int) {
        launchResource(
            apiCall = unregisterEventUseCase(eventId),
            onLoading = {
                updateUiState { copy(isLoading = isLoading) }
            },
            onSuccess = {
                updateUiState {
                    copy(
                        isRegistered = false
                    )
                }
            },
            onError = {}
        )
    }

    private fun registerEvent(eventId: Int) {
        launchResource(
            apiCall = registerEventUseCase(eventId),
            onLoading = {
                updateUiState { copy(isLoading = isLoading) }
            },
            onSuccess = {
                updateUiState {
                    copy(
                        isRegistered = true
                    )
                }
            },
            onError = {}
        )
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