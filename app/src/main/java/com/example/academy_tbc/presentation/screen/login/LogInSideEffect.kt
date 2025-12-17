package com.example.academy_tbc.presentation.screen.login

import com.example.academy_tbc.presentation.util.GenericString

sealed interface LogInSideEffect {
    data object NavigateToHome : LogInSideEffect
    data class ShowError(val error: GenericString) : LogInSideEffect

}