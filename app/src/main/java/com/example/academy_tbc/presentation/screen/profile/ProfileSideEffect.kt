package com.example.academy_tbc.presentation.screen.profile

sealed interface ProfileSideEffect {
    data object NavigateToLogIn : ProfileSideEffect
}