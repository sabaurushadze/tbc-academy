package com.example.academy_tbc.data.repository.event_registration

import com.example.academy_tbc.data.common.ApiResponseHandler
import com.example.academy_tbc.data.common.mapResource
import com.example.academy_tbc.data.dto.request.event_registration.EventRegistrationRequestDto
import com.example.academy_tbc.data.mapper.auth.event_registration.toDomain
import com.example.academy_tbc.data.service.event_registration.EventRegistrationService
import com.example.academy_tbc.domain.common.ApiError
import com.example.academy_tbc.domain.common.Resource
import com.example.academy_tbc.domain.model.auth.event_registration.EventRegistration
import com.example.academy_tbc.domain.repository.event_registration.EventRegistrationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class EventRegistrationRepositoryImpl @Inject constructor(
    private val api: EventRegistrationService,
    private val responseHandler: ApiResponseHandler,
) : EventRegistrationRepository {
    override fun registerEvent(eventId: Int): Flow<Resource<Unit, ApiError>> {
        return responseHandler.safeApiCall {
            api.registerEvent(EventRegistrationRequestDto(eventId))
        }
    }

    override fun unregisterEvent(eventId: Int): Flow<Resource<Unit, ApiError>> {
        return responseHandler.safeApiCall {
            api.unregisterEvent(eventId)
        }
    }

    override fun checkEventRegistration(eventId: Int): Flow<Resource<EventRegistration, ApiError>> {
        return responseHandler.safeApiCall {
            api.checkEventRegistration(eventId)
        }.mapResource {
            it.toDomain()
        }
    }
}