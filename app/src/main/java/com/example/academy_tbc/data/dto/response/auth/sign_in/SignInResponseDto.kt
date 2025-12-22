package com.example.academy_tbc.data.dto.response.auth.sign_in

import kotlinx.serialization.Serializable

@Serializable
data class SignInResponseDto(
    val token: String
)