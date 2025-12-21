package com.example.academy_tbc.presentation.screen.auth.forgot_password

import com.example.academy_tbc.presentation.util.GenericString

sealed interface ForgotPasswordSideEffect {
    data class ShowError(val error: GenericString) : ForgotPasswordSideEffect

}