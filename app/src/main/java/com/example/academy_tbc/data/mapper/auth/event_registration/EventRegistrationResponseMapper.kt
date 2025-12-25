package com.example.academy_tbc.data.mapper.auth.event_registration

import com.example.academy_tbc.data.dto.response.auth.event_registration.EventRegistrationResponseDto
import com.example.academy_tbc.domain.model.auth.event_registration.EventRegistration

fun EventRegistrationResponseDto.toDomain() =
    EventRegistration(
        isRegistered = isRegistered
    )