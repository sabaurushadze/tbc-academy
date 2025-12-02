package com.example.academy_tbc.presentation.screen.register

import androidx.annotation.StringRes

sealed interface RegisterSideEffect {
    data class NavigateToLogin(val email: String, val password: String) : RegisterSideEffect
    data class ShowError(@param:StringRes val error: Int) : RegisterSideEffect
}