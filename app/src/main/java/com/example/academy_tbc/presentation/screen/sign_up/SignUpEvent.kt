package com.example.academy_tbc.presentation.screen.sign_up

sealed class SignUpEvent {
    data class EmailChanged(val value: String) : SignUpEvent()
    data class PasswordChanged(val value: String) : SignUpEvent()
    data class SignUpWithEmailAndPassword(val email: String, val password: String) : SignUpEvent()
}
