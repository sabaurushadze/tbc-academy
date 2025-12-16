package com.example.academy_tbc.presentation.screen.sign_in

import com.example.academy_tbc.presentation.common.text.GenericString


sealed interface SignInSideEffect {
    data object NavigateToHome : SignInSideEffect
    data class ShowError(val error: GenericString) : SignInSideEffect
}