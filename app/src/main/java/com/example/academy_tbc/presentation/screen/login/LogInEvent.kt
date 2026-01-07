package com.example.academy_tbc.presentation.screen.login

sealed class LogInEvent {
    data class LogIn(val email: String, val password: String, val rememberMe: Boolean) : LogInEvent()
}