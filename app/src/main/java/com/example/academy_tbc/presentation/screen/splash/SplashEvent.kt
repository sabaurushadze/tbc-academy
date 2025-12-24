package com.example.academy_tbc.presentation.screen.splash


sealed class SplashEvent {
    data object OnStartSplash : SplashEvent()
    data object OnStopSplash : SplashEvent()
}