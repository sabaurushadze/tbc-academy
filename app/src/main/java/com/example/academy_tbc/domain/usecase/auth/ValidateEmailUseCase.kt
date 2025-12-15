package com.example.academy_tbc.domain.usecase.auth

import android.util.Patterns
import com.example.academy_tbc.domain.model.auth.AuthValidationError
import javax.inject.Inject

class ValidateEmailUseCase @Inject constructor() {
    operator fun invoke(email: String): AuthValidationError {
        return when {
            email.isBlank() -> AuthValidationError.EMAIL_EMPTY
            email.length < 6 -> AuthValidationError.EMAIL_TOO_SHORT
            email.length > 28 -> AuthValidationError.EMAIL_TOO_LONG
            !Patterns.EMAIL_ADDRESS.matcher(email)
                .matches() -> AuthValidationError.EMAIL_WRONG_FORMAT

            else -> AuthValidationError.UNKNOWN
        }
    }
}