package com.example.academy_tbc.presentation.screen.auth.sign_in


sealed class SignInEvent {
    data class SignIn(val email: String, val password: String, val rememberMe: Boolean) : SignInEvent()
}