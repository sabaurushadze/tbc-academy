package com.example.academy_tbc.presentation.screen.home

sealed interface HomeSideEffect {
    data class ShowError(val message: String) : HomeSideEffect
    data object NavigateToSignIn : HomeSideEffect
}