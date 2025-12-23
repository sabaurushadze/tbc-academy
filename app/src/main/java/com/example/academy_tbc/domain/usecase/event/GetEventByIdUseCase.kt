package com.example.academy_tbc.domain.usecase.event

import com.example.academy_tbc.domain.common.ApiError
import com.example.academy_tbc.domain.common.Resource
import com.example.academy_tbc.domain.common.ResourceError
import com.example.academy_tbc.domain.model.home.upcoming_events.Event
import com.example.academy_tbc.domain.repository.events.EventRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetEventByIdUseCase @Inject constructor(
    private val eventRepository: EventRepository,
) {
    operator fun invoke(id: Int): Flow<Resource<Event, ApiError>> {
        return eventRepository.getEventById(id)
    }
}