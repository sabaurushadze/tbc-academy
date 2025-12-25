package com.example.academy_tbc.presentation.screen.events.browse_events

import com.example.academy_tbc.domain.usecase.category.GetCategoriesUseCase
import com.example.academy_tbc.domain.usecase.event.GetEventsUseCase
import com.example.academy_tbc.presentation.common.mapper.toGenericString
import com.example.academy_tbc.presentation.common.view.BaseViewModel
import com.example.academy_tbc.presentation.screen.events.browse_events.categories.mapper.toEventCategoryUi
import com.example.academy_tbc.presentation.screen.events.browse_events.categories.model.EventCategoryUi
import com.example.academy_tbc.presentation.screen.events.browse_events.events.mapper.localDate
import com.example.academy_tbc.presentation.screen.events.browse_events.events.mapper.toEventUi
import com.example.academy_tbc.presentation.screen.events.browse_events.events.model.EventUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime
import javax.inject.Inject
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@HiltViewModel
class EventsViewModel @Inject constructor(
    private val getEventsUseCase: GetEventsUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase,
) : BaseViewModel<EventsState, EventsSideEffect, EventsEvent>(EventsState()) {

    init {
        getEvents()
        getCategories()
    }

    override fun onEvent(event: EventsEvent) {
        when (event) {
            is EventsEvent.SaveCategory -> saveCategory(event.eventCategory)
            is EventsEvent.Search -> search(event.query)
            is EventsEvent.ApplyFilters -> applyEventFilters(event)
            EventsEvent.GetCategories -> getCategories()
            EventsEvent.GetEvents -> getEvents()
        }

    }

    private fun search(query: String) {
        updateUiState {
            copy(
                searchQuery = query,
                events = applyFilters(
                    allEvents = allEvents,
                    query = query,
                    location = filterLocation,
                    date = filterDate,
                    onlyAvailable = filterOnlyAvailable
                )
            )
        }
    }


    private fun applyEventFilters(event: EventsEvent.ApplyFilters) {
        updateUiState {
            copy(
                filterLocation = event.location,
                filterDate = event.date,
                filterOnlyAvailable = event.onlyAvailable,
                events = applyFilters(
                    allEvents = allEvents,
                    query = searchQuery,
                    location = event.location,
                    date = event.date,
                    onlyAvailable = event.onlyAvailable
                )
            )
        }
    }

    @OptIn(ExperimentalTime::class)
    private fun applyFilters(
        allEvents: List<EventUi>,
        categoryId: Int = -1,
        query: String = "",
        location: String = "",
        date: String = "",
        onlyAvailable: Boolean = false,
    ): List<EventUi> {
        val today = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date

        return allEvents.filter { event ->
            (categoryId == -1 || event.categoryId == categoryId) &&
                    (query.isBlank() || event.title.contains(query, ignoreCase = true)) &&
                    (location.isBlank() || event.location.contains(location, ignoreCase = true)) &&
                    (!onlyAvailable || event.eventStatus.equals("available", ignoreCase = true)) &&
                    when (date) {
                        "All" -> true
                        "Today" -> event.localDate() == today
                        "This week" -> {
                            val startOfWeek = today.minus(today.dayOfWeek.ordinal, DateTimeUnit.DAY)
                            val endOfWeek = startOfWeek.plus(6, DateTimeUnit.DAY)
                            event.localDate() in startOfWeek..endOfWeek
                        }

                        "This month" -> event.localDate().month == today.month && event.localDate().year == today.year
                        else -> true
                    }
        }
    }


    private fun getCategories() {
        launchResource(
            apiCall = getCategoriesUseCase(),
            onLoading = { loading ->
                updateUiState {
                    copy(isLoading = loading)
                }
            },
            onSuccess = { categories ->
                val uiCategories = categories.map { it.toEventCategoryUi() }

                val allCategory = EventCategoryUi(
                    id = -1,
                    title = "All",
                    selected = true
                )

                val selectedId = state.value.selectedCategoryId

                val updatedCategories = (listOf(allCategory) + uiCategories).map {
                    it.copy(selected = it.id == selectedId)
                }

                updateUiState {
                    copy(
                        eventCategories = updatedCategories
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
            onLoading = { loading ->
                updateUiState { copy(isLoading = loading) }
            },
            onSuccess = { events ->
                val uiEvents = events.map { it.toEventUi() }
                updateUiState {
                    val selectedCategoryId = state.value.selectedCategoryId
                    copy(
                        allEvents = uiEvents,
                        events = applyFilters(
                            allEvents = uiEvents,
                            categoryId = selectedCategoryId,
                            query = state.value.searchQuery,
                            location = state.value.filterLocation,
                            date = state.value.filterDate,
                            onlyAvailable = state.value.filterOnlyAvailable
                        )
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
            val updatedCategories =
                eventCategories.map { it.copy(selected = it.id == categoryEventId) }
            copy(
                selectedCategoryId = categoryEventId,
                eventCategories = updatedCategories,
                events = applyFilters(
                    allEvents = allEvents,
                    categoryId = categoryEventId,
                    query = searchQuery,
                    location = filterLocation,
                    date = filterDate,
                    onlyAvailable = filterOnlyAvailable
                )
            )
        }
    }
}