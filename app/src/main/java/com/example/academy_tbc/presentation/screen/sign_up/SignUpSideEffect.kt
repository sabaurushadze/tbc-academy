package com.example.academy_tbc.presentation.screen.sign_up

import com.example.academy_tbc.presentation.common.text.GenericString

sealed interface SignUpSideEffect {
    data object NavigateToHome : SignUpSideEffect
    data class ShowError(val error: GenericString) : SignUpSideEffect
}