package com.example.academy_tbc.data.remote.dto.request.login

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequestDto(
    val email: String,
    val password: String
)