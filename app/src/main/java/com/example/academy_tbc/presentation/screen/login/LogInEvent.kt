package com.example.academy_tbc.presentation.screen.login


sealed class LogInEvent {
    data class LogIn(val email: String, val password: String, val isRemembered: Boolean) : LogInEvent()
    data class EmailChanged(val email: String) : LogInEvent()
    data class PasswordChanged(val password: String) : LogInEvent()
    data class RememberMeChanged(val isRemembered: Boolean) : LogInEvent()
}