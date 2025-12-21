package com.example.academy_tbc.presentation.screen.home

sealed class HomeEvent {
    data class SendOtp(val phoneNumber: String) : HomeEvent()
    data class ResendOtp(val phoneNumber: String) : HomeEvent()
    data class ValidateCode(val otpCode: String) : HomeEvent()
    data class SignUp(
        val firstName: String,
        val lastName: String,
        val email: String,
        val password: String,
        val confirmPassword: String,
        ) : HomeEvent()
}