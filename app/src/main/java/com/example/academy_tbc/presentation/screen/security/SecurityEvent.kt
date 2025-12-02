package com.example.academy_tbc.presentation.screen.security

sealed class SecurityEvent {
    data class NumberPressed(val value: String) : SecurityEvent()
    object Backspace : SecurityEvent()
    object BiometricClick : SecurityEvent()
}