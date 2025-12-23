package com.example.academy_tbc.presentation.screen.events.browse_events

import com.example.academy_tbc.domain.usecase.category.GetCategoriesUseCase
import com.example.academy_tbc.domain.usecase.event.GetEventsUseCase
import com.example.academy_tbc.presentation.common.mapper.toGenericString
import com.example.academy_tbc.presentation.common.view.BaseViewModel
import com.example.academy_tbc.presentation.screen.events.browse_events.categories.mapper.toEventCategoryUi
import com.example.academy_tbc.presentation.screen.events.browse_events.events.mapper.toEventUi
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EventsViewModel @Inject constructor(
    private val getEventsUseCase: GetEventsUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase
) : BaseViewModel<EventsState, EventsSideEffect, EventsEvent>(EventsState()) {


    override fun onEvent(event: EventsEvent) {
        when (event) {
            is EventsEvent.SaveCategory -> saveCategory(event.eventCategory)
            EventsEvent.GetCategories -> getCategories()
            EventsEvent.GetEvents -> getEvents()
        }

    }

    private fun getCategories() {
        launchResource(
            apiCall = getCategoriesUseCase(),
            onLoading = {
                updateUiState { copy(isLoading = isLoading) }
            },
            onSuccess = { categories ->
                updateUiState {
                    copy(
                        eventCategories = categories.map { it.toEventCategoryUi() }
                    )
                }
            },
            onError = {
                emitSideEffect(EventsSideEffect.ShowError(error = it.toGenericString()))
            }
        )
    }

    private fun getEvents() {
        launchResource(
            apiCall = getEventsUseCase(),
            onLoading = {
                updateUiState { copy(isLoading = isLoading) }
            },
            onSuccess = { events ->
                updateUiState {
                    copy(
                        events = events.map { it.toEventUi() }
                    )
                }
            },
            onError = {
                emitSideEffect(EventsSideEffect.ShowError(error = it.toGenericString()))
            }
        )
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