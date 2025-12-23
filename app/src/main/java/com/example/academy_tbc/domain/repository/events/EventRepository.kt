package com.example.academy_tbc.domain.repository.events

import com.example.academy_tbc.domain.common.ApiError
import com.example.academy_tbc.domain.common.Resource
import com.example.academy_tbc.domain.common.ResourceError
import com.example.academy_tbc.domain.model.home.upcoming_events.Event
import kotlinx.coroutines.flow.Flow

interface EventRepository {
    fun getEvents(): Flow<Resource<List<Event>, ApiError>>
    fun getEventById(id: Int): Flow<Resource<Event, ApiError>>
}