package com.example.academy_tbc.presentation.screen.login

import androidx.annotation.StringRes

sealed interface LogInSideEffect {
    data object NavigateToHome : LogInSideEffect
    data class ShowSnackBar(@param:StringRes val errorRes: Int) : LogInSideEffect

}