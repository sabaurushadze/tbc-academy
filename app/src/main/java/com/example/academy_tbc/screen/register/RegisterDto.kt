package com.example.academy_tbc.screen.register

import kotlinx.serialization.Serializable

@Serializable
data class RegisterDto(
    val email: String,
    val password: String
)

@Serializable
data class RegisterResponseDto(
    val id: Int,
    val token: String
)