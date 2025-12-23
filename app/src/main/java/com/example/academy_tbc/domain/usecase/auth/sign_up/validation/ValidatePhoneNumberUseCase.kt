package com.example.academy_tbc.domain.usecase.auth.sign_up.validation

import com.example.academy_tbc.domain.model.auth.sign_up.SignUpValidationError
import jakarta.inject.Inject

class ValidatePhoneNumberUseCase @Inject constructor() {
    private val phoneRegex = Regex("^\\+995\\d{9}$")
    operator fun invoke(phone: String): SignUpValidationError {
        return when {
            phone.isBlank() -> SignUpValidationError.WRONG_PHONE_NUMBER_FORMAT

            !phoneRegex.matches(phone) -> SignUpValidationError.WRONG_PHONE_NUMBER_FORMAT

            else -> SignUpValidationError.VALID
        }
    }
}