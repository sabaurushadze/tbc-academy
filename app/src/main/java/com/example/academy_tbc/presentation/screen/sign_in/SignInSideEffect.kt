package com.example.academy_tbc.presentation.screen.sign_in


sealed interface SignInSideEffect {
    data object NavigateToHome : SignInSideEffect
    data class ShowError(val message: String) : SignInSideEffect
}