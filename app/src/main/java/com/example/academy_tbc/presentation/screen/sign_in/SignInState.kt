package com.example.academy_tbc.presentation.screen.sign_in

data class SignInState(
    val isSignInSuccessful: Boolean = false,
    val user: UserData? = null
)