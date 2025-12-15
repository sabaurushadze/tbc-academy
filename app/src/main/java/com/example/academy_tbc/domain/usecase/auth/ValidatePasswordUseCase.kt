package com.example.academy_tbc.domain.usecase.auth

import com.example.academy_tbc.domain.model.auth.AuthValidationError
import javax.inject.Inject

class ValidatePasswordUseCase @Inject constructor() {
    operator fun invoke(password: String): AuthValidationError {
        return when {
            password.isBlank() -> AuthValidationError.PASSWORD_EMPTY
            password.length < 6 -> AuthValidationError.PASSWORD_TOO_SHORT
            password.length > 28 -> AuthValidationError.PASSWORD_TOO_LONG
            else -> AuthValidationError.UNKNOWN
        }
    }
}