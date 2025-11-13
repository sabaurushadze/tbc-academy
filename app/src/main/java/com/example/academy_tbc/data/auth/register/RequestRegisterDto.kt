package com.example.academy_tbc.data.auth.register

import kotlinx.serialization.Serializable

@Serializable
data class RequestRegisterDto(
    val email: String,
    val password: String
)
