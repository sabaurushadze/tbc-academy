package com.example.academy_tbc.data.auth.login

import kotlinx.serialization.Serializable

@Serializable
data class RequestLoginDto(
    val email: String, val password: String
)
