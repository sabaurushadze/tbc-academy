package com.example.academy_tbc.domain.usecase.auth.sign_up.validation

import com.example.academy_tbc.domain.model.auth.sign_up.SignUpValidationError
import jakarta.inject.Inject

class ValidateFirstNameUseCase @Inject constructor() {
    operator fun invoke(firstName: String): SignUpValidationError {
        return when {
            firstName.isBlank() -> SignUpValidationError.FIRST_NAME_EMPTY
            else -> SignUpValidationError.VALID
        }
    }
}