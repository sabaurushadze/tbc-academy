package com.example.academy_tbc.data.model.request.register

import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequestDto(
    val email: String,
    val password: String
)