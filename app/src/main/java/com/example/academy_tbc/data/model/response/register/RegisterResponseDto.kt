package com.example.academy_tbc.data.model.response.register

import kotlinx.serialization.Serializable

@Serializable
data class RegisterResponseDto(
    val id: Int,
    val token: String
)