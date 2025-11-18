package com.example.academy_tbc.presentation.screen.register

sealed class RegisterEvent {
    data class Register(val email: String, val password: String) : RegisterEvent()
    data class EmailChanged(val email: String) : RegisterEvent()
    data class PasswordChanged(val password: String) : RegisterEvent()
    data class RepeatPasswordChanged(val repeatPassword: String) : RegisterEvent()
    data object BackPressed : RegisterEvent()
}