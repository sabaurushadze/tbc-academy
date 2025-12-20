package com.example.academy_tbc.presentation.screen.sign_in

import com.example.academy_tbc.presentation.util.GenericString

sealed interface SignInSideEffect {
    data class ShowError(val error: GenericString) : SignInSideEffect

}