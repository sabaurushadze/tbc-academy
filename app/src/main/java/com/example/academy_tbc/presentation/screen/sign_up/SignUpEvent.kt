package com.example.academy_tbc.presentation.screen.sign_up

sealed class SignUpEvent {
    data object SendOtpClicked : SignUpEvent()
    data object ResendOtpClicked : SignUpEvent()
}