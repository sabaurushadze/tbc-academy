package com.example.academy_tbc.presentation.screen.register

data class RegisterState(
    val email: String = "",
    val password: String = "",
    val repeatPassword: String = "",
    val isRegisterEnabled: Boolean = false,
    val isLoading: Boolean = false
)
