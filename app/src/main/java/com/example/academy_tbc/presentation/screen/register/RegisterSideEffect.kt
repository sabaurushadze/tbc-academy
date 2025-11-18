package com.example.academy_tbc.presentation.screen.register

sealed interface RegisterSideEffect {
    data class NavigateToLogin(val email: String, val password: String) : RegisterSideEffect
    data class ShowError(val message: String) : RegisterSideEffect
    data object NavigateBack : RegisterSideEffect
}