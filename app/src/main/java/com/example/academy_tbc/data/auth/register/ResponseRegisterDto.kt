package com.example.academy_tbc.data.auth.register

import kotlinx.serialization.Serializable

@Serializable
data class ResponseRegisterDto(
    val id: Int,
    val token: String
)