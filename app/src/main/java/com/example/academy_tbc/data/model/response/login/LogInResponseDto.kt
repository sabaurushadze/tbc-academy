package com.example.academy_tbc.data.model.response.login

import kotlinx.serialization.Serializable

@Serializable
data class LogInResponseDto(
    val token: String
)