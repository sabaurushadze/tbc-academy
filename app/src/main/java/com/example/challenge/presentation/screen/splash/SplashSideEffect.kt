package com.example.challenge.presentation.screen.splash

sealed interface SplashSideEffect {
    data object NavigateToLogIn : SplashSideEffect
    data object NavigateToConnections : SplashSideEffect
}