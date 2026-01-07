package com.example.academy_tbc.presentation.screen.login

data class LogInState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val rememberMe: Boolean = false,
)