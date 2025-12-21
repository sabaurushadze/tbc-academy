package com.example.academy_tbc.data.model.request.auth.sign_in

import kotlinx.serialization.Serializable

@Serializable
data class SignInRequestDto(
    val email: String,
    val password: String
)