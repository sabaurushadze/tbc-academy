package com.example.academy_tbc.data.dto.response.auth.event_registration

import kotlinx.serialization.Serializable

@Serializable
data class EventRegistrationResponseDto(
    val isRegistered: Boolean
)