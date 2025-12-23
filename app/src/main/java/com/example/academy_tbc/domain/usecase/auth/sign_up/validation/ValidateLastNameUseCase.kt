package com.example.academy_tbc.domain.usecase.auth.sign_up.validation

import com.example.academy_tbc.domain.model.auth.sign_up.SignUpValidationError
import jakarta.inject.Inject

class ValidateLastNameUseCase @Inject constructor() {
    operator fun invoke(lastName: String): SignUpValidationError {
        return when {
            lastName.isBlank() -> SignUpValidationError.LAST_NAME_EMPTY
            else -> SignUpValidationError.VALID
        }
    }
}