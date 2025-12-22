package com.example.academy_tbc.presentation.screen.auth.sign_in

import com.example.academy_tbc.presentation.util.GenericString

sealed interface SignInSideEffect {
    data class ShowError(val error: GenericString) : SignInSideEffect
    data class ShowEmailError(val error: GenericString) : SignInSideEffect
    data class ShowPasswordError(val error: GenericString) : SignInSideEffect

    data object NavigateToHome : SignInSideEffect
}