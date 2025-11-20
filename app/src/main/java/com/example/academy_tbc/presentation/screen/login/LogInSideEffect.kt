package com.example.academy_tbc.presentation.screen.login

sealed interface LogInSideEffect {
    data object NavigateToHome : LogInSideEffect
    data class ShowError(val message: String) : LogInSideEffect
}