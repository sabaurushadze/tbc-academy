package com.example.academy_tbc.data.login

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponseDto(
    val token: String
)
