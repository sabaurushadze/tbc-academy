package com.example.academy_tbc.data.dto.request.auth.sign_up

import kotlinx.serialization.Serializable

@Serializable
data class SignUpRequestDto(
    val firstName: String,
    val lastName: String,
    val email: String,
    val phoneNumber: String,
    val department: String,
    val password: String,
)
