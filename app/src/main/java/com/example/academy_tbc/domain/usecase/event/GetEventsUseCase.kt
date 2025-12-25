package com.example.academy_tbc.domain.usecase.event

import com.example.academy_tbc.domain.common.ApiError
import com.example.academy_tbc.domain.common.Resource
import com.example.academy_tbc.domain.model.home.upcoming_events.Event
import com.example.academy_tbc.domain.repository.events.EventRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetEventsUseCase @Inject constructor(
    private val eventRepository: EventRepository,
) {
    operator fun invoke(pages: Int? = null): Flow<Resource<List<Event>, ApiError>> {
        return eventRepository.getEvents(pages)
    }
}