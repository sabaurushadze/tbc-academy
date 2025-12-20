package com.example.academy_tbc.presentation.screen.sign_up

import com.example.academy_tbc.presentation.util.GenericString

sealed interface SignUpSideEffect {
    data class ShowError(val error: GenericString) : SignUpSideEffect
    data object OtpExpired : SignUpSideEffect

}