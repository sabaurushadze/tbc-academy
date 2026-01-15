package com.example.academy_tbc.presentation.screen.register_username

import androidx.annotation.StringRes

sealed interface RegisterUsernameSideEffect {
    data class ShowSnackBar(@param:StringRes val errorRes: Int) : RegisterUsernameSideEffect
    data object NavigateToHome : RegisterUsernameSideEffect
}