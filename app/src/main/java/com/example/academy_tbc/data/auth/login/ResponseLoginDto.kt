package com.example.academy_tbc.data.auth.login

import kotlinx.serialization.Serializable

@Serializable
data class ResponseLoginDto(
    val token: String
)
