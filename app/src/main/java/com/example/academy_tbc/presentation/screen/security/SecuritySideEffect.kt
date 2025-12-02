package com.example.academy_tbc.presentation.screen.security

sealed interface SecuritySideEffect {
    data object Success : SecuritySideEffect
    data object WrongCode : SecuritySideEffect
    data object LaunchBiometric : SecuritySideEffect
}