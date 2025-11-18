package com.example.academy_tbc.data.remote.login

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequestDto(
    val email: String, val password: String
)
