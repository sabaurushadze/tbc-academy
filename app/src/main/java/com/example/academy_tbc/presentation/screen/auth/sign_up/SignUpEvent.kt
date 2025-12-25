package com.example.academy_tbc.presentation.screen.auth.sign_up

sealed class SignUpEvent {
    data class SendOtp(val phoneNumber: String) : SignUpEvent()
    data class ResendOtp(val phoneNumber: String) : SignUpEvent()
    data class ValidateCode(val otpCode: String) : SignUpEvent()
    data class SaveDepartment(val department: Int) : SignUpEvent()
    data class SignUp(
        val firstName: String,
        val lastName: String,
        val email: String,
        val password: String,
        val confirmPassword: String,
        val department: Int,
        val phoneNumber: String
        ) : SignUpEvent()
}