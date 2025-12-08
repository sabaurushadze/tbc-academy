package com.example.academy_tbc.presentation.screen.sign_in

sealed class SignInEvent {
    data class SignInWithEmailAndPassword(val email: String, val password: String) : SignInEvent()
    data class SignInWithGoogle(val idToken: String) : SignInEvent()
}
