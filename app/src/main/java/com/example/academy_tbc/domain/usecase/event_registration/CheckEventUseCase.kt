package com.example.academy_tbc.domain.usecase.event_registration

import com.example.academy_tbc.domain.common.ApiError
import com.example.academy_tbc.domain.common.Resource
import com.example.academy_tbc.domain.model.auth.event_registration.EventRegistration
import com.example.academy_tbc.domain.repository.event_registration.EventRegistrationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CheckEventUseCase @Inject constructor(
    private val eventRegistrationRepository: EventRegistrationRepository,
) {
    operator fun invoke(eventId: Int): Flow<Resource<EventRegistration, ApiError>> {
        return eventRegistrationRepository.checkEventRegistration(eventId)
    }
}