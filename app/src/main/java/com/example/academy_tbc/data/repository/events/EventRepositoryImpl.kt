package com.example.academy_tbc.data.repository.events

import com.example.academy_tbc.data.common.ApiResponseHandler
import com.example.academy_tbc.data.common.mapResource
import com.example.academy_tbc.data.mapper.home.upcoming_events.toDomain
import com.example.academy_tbc.data.service.events.EventService
import com.example.academy_tbc.domain.common.ApiError
import com.example.academy_tbc.domain.common.Resource
import com.example.academy_tbc.domain.common.ResourceError
import com.example.academy_tbc.domain.model.home.upcoming_events.Event
import com.example.academy_tbc.domain.repository.events.EventRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class EventRepositoryImpl @Inject constructor(
    private val api: EventService,
    private val responseHandler: ApiResponseHandler,
) : EventRepository {
    override fun getEvents(): Flow<Resource<List<Event>, ApiError>> {
        return responseHandler.safeApiCall {
            api.getEvents()
        }.mapResource {
            it.toDomain()
        }
    }

    override fun getEventById(id: Int): Flow<Resource<Event, ApiError>> {
        return responseHandler.safeApiCall {
            api.getEventById(id)
        }.mapResource {
            it.toDomain()
        }
    }
}