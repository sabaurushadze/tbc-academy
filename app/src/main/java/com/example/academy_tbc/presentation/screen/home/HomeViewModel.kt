package com.example.academy_tbc.presentation.screen.home

import com.example.academy_tbc.domain.usecase.category.GetCategoriesUseCase
import com.example.academy_tbc.domain.usecase.event.GetEventsUseCase
import com.example.academy_tbc.presentation.common.mapper.toGenericString
import com.example.academy_tbc.presentation.common.view.BaseViewModel
import com.example.academy_tbc.presentation.screen.home.categories.mapper.toCategoryUi
import com.example.academy_tbc.presentation.screen.home.upcoming_events.mapper.toUpcomingEventUi
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getEventsUseCase: GetEventsUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase,
) : BaseViewModel<HomeState, HomeSideEffect, HomeEvent>(HomeState()) {


    init {
        getCategories()
        getEvents()
    }

    override fun onEvent(event: HomeEvent) {
        when (event) {
            HomeEvent.GetCategories -> getCategories()
            HomeEvent.GetEvents -> getEvents()
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
                        categories = categories.map { it.toCategoryUi() }
                    )
                }
            },
            onError = {
                emitSideEffect(HomeSideEffect.ShowError(error = it.toGenericString()))
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
                        events = events.map { it.toUpcomingEventUi() }
                    )
                }
            },
            onError = {
                emitSideEffect(HomeSideEffect.ShowError(error = it.toGenericString()))
            }
        )
    }

}