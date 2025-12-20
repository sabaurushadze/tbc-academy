package com.example.academy_tbc.presentation.screen.sign_up

data class SignUpState(
    val isLoading: Boolean = false,
    val isOtpVisible: Boolean = false,
    val elapsedTime: Long = 0
)