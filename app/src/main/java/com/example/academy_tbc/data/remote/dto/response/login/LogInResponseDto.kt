package com.example.academy_tbc.data.remote.dto.response.login

import kotlinx.serialization.Serializable

@Serializable
data class LogInResponseDto(
    val token: String
)