package com.example.academy_tbc.domain.repository.event_registration

import com.example.academy_tbc.domain.common.ApiError
import com.example.academy_tbc.domain.common.Resource
import com.example.academy_tbc.domain.model.auth.event_registration.EventRegistration
import kotlinx.coroutines.flow.Flow

interface EventRegistrationRepository {
    fun registerEvent(eventId: Int): Flow<Resource<Unit, ApiError>>

    fun unregisterEvent(eventId: Int): Flow<Resource<Unit, ApiError>>

    fun checkEventRegistration(eventId: Int): Flow<Resource<EventRegistration, ApiError>>
}