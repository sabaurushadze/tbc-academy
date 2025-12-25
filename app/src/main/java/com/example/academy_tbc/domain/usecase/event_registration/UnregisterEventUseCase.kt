package com.example.academy_tbc.domain.usecase.event_registration

import com.example.academy_tbc.domain.common.ApiError
import com.example.academy_tbc.domain.common.Resource
import com.example.academy_tbc.domain.repository.event_registration.EventRegistrationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UnregisterEventUseCase @Inject constructor(
    private val eventRegistrationRepository: EventRegistrationRepository,
) {
    operator fun invoke(eventId: Int): Flow<Resource<Unit, ApiError>> {
        return eventRegistrationRepository.unregisterEvent(eventId)
    }
}