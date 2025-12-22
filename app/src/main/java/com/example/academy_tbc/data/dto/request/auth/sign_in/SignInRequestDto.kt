package com.example.academy_tbc.data.dto.request.auth.sign_in

import kotlinx.serialization.Serializable

@Serializable
data class SignInRequestDto(
    val email: String,
    val password: String
)