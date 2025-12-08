package com.example.academy_tbc.presentation.screen.sign_up

sealed interface SignUpSideEffect {
    data object NavigateToHome : SignUpSideEffect
    data class ShowError(val message: String) : SignUpSideEffect
}