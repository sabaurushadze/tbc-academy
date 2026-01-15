package com.example.academy_tbc.presentation.screen.login

sealed class LogInEvent {
    data object LogIn : LogInEvent()
    data class EmailChanged(val email: String) : LogInEvent()
    data class PasswordChanged(val password: String) : LogInEvent()
}