package com.example.academy_tbc.domain.usecase.auth.sign_up.validation

import com.example.academy_tbc.domain.model.auth.sign_up.SignUpValidationError
import jakarta.inject.Inject

class ValidateOtpCodeUseCase @Inject constructor() {
    private val otpRegex = Regex("^\\d{6}$")
    operator fun invoke(otp: String): SignUpValidationError {
        return when {
            otp.isBlank() -> SignUpValidationError.WRONG_OTP_FORMAT

            !otpRegex.matches(otp) -> SignUpValidationError.WRONG_OTP_FORMAT
            else -> SignUpValidationError.VALID
        }
    }
}