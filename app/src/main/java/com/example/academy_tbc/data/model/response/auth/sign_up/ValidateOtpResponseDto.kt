package com.example.academy_tbc.data.model.response.auth.sign_up

import kotlinx.serialization.Serializable

@Serializable
data class ValidateOtpResponseDto(
    val isValid: Boolean
)