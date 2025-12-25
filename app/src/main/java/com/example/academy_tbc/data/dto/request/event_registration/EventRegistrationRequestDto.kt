package com.example.academy_tbc.data.dto.request.event_registration

import kotlinx.serialization.Serializable


@Serializable
data class EventRegistrationRequestDto(
    val eventId: Int
)