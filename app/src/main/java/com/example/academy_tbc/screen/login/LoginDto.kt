package com.example.academy_tbc.screen.login

import kotlinx.serialization.Serializable

@Serializable
data class LoginDto(
    val email: String, val password: String
)

@Serializable
data class LoginResponseDto(
    val token: String
)
