package com.example.academy_tbc.data.model.request.login

import kotlinx.serialization.Serializable

@Serializable
data class LogInRequestDto(
    val email: String, val password: String
)