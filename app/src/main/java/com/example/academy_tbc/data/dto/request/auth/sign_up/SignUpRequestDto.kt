package com.example.academy_tbc.data.dto.request.auth.sign_up

import kotlinx.serialization.Serializable

@Serializable
data class SignUpRequestDto(
    val email: String,
    val password: String,
    val firstName: String,
    val lastName: String,
    val phoneNumber: String,
    val departmentId: Int,
)

