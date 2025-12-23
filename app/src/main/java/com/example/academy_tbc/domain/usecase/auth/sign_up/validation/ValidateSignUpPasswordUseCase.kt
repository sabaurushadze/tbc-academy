package com.example.academy_tbc.domain.usecase.auth.sign_up.validation

import com.example.academy_tbc.domain.model.auth.sign_up.SignUpValidationError
import jakarta.inject.Inject

class ValidateSignUpPasswordUseCase @Inject constructor() {
    private val passwordRegex = Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$")
    operator fun invoke(password: String): SignUpValidationError {
        return when {
            password.isBlank() -> SignUpValidationError.PASSWORD_EMPTY
            !passwordRegex.matches(password) -> SignUpValidationError.PASSWORD_WRONG_FORMAT

            else -> SignUpValidationError.VALID
        }
    }
}