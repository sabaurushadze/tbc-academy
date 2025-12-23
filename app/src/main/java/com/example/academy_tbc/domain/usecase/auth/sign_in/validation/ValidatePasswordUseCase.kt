package com.example.academy_tbc.domain.usecase.auth.sign_in.validation

import com.example.academy_tbc.domain.model.auth.sign_in.SignInValidationError
import jakarta.inject.Inject

class ValidatePasswordUseCase @Inject constructor() {
    operator fun invoke(password: String): SignInValidationError {
        return when {
            password.isBlank() -> SignInValidationError.PASSWORD_EMPTY

            else -> SignInValidationError.VALID
        }
    }
}