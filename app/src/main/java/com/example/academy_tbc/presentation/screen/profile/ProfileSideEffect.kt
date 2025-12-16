package com.example.academy_tbc.presentation.screen.profile

import com.example.academy_tbc.presentation.common.text.GenericString

sealed interface ProfileSideEffect {
    data class ShowError(val error: GenericString) : ProfileSideEffect
    data object NavigateToSignIn : ProfileSideEffect
    data object UpdateUserNameSuccess : ProfileSideEffect
}