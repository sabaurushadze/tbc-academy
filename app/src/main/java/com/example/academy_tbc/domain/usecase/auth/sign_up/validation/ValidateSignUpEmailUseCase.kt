package com.example.academy_tbc.domain.usecase.auth.sign_up.validation

import android.util.Patterns
import com.example.academy_tbc.domain.model.auth.sign_up.SignUpValidationError
import jakarta.inject.Inject

class ValidateSignUpEmailUseCase @Inject constructor() {
    operator fun invoke(email: String): SignUpValidationError {
        return when {
            email.isBlank() -> SignUpValidationError.EMAIL_EMPTY
            !Patterns.EMAIL_ADDRESS.matcher(email)
                .matches() -> SignUpValidationError.EMAIL_WRONG_FORMAT

            else -> SignUpValidationError.VALID
        }
    }
}