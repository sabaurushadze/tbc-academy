package com.example.academy_tbc.data.dto.response.auth.sign_in

import kotlinx.serialization.Serializable

@Serializable
data class SignInResponseDto(
    val token: String,
    val email: String,
    val fullName: String,
    val phoneNumber: String,
    val department: String,
)