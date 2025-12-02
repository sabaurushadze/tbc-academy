package com.example.academy_tbc.presentation.screen.login

import androidx.annotation.StringRes

sealed interface LogInSideEffect {
    data object NavigateToHome : LogInSideEffect
    data class ShowError(@param:StringRes val error: Int) : LogInSideEffect
}