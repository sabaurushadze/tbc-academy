package com.example.academy_tbc.presentation.screen.splash

sealed interface SplashSideEffect {
    data object NavigateToHome : SplashSideEffect
    data object NavigateToSignIn : SplashSideEffect
}