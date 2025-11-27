package com.example.academy_tbc.data.remote.login.model

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponseDto(
    val token: String
)
