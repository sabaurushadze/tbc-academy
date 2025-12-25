package com.example.academy_tbc.domain.model.auth.sign_in

data class AuthToken(
    val token: String,
    val email: String,
    val fullName: String,
    val phoneNumber: String,
    val department: String,
)