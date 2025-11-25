package com.example.academy_tbc.presentation.screen.login

import com.example.academy_tbc.common.AppException

sealed interface LogInSideEffect {
    data object NavigateToHome : LogInSideEffect
    data class ShowError(val exception: AppException) : LogInSideEffect
}