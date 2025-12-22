package com.example.academy_tbc.data.dto.response.auth.sign_up

import kotlinx.serialization.Serializable

@Serializable
data class ValidateOtpResponseDto(
    val isValid: Boolean
)