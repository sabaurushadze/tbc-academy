package com.example.challenge.presentation.screen.connection

sealed interface ConnectionSideEffect {
    data object NavigateToLogIn : ConnectionSideEffect
}