package com.example.academy_tbc.domain.usecase.auth.sign_in.validation

import android.util.Patterns
import com.example.academy_tbc.domain.model.auth.sign_in.SignInValidationError
import jakarta.inject.Inject

class ValidateEmailUseCase @Inject constructor() {
    operator fun invoke(email: String): SignInValidationError {
        return when {
            email.isBlank() -> SignInValidationError.EMAIL_EMPTY
             !Patterns.EMAIL_ADDRESS.matcher(email)
                .matches() -> SignInValidationError.EMAIL_WRONG_FORMAT

            else -> SignInValidationError.VALID
        }
    }
}