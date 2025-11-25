package com.example.academy_tbc.presentation.screen.register

import com.example.academy_tbc.common.AppException

sealed interface RegisterSideEffect {
    data class NavigateToLogin(val email: String, val password: String) : RegisterSideEffect
    data class ShowError(val exception: AppException) : RegisterSideEffect
}