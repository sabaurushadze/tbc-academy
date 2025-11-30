package com.example.academy_tbc.domain.usecase.validations

import com.example.academy_tbc.domain.validator.EmailValidator
import javax.inject.Inject

class EmailValidationUseCase @Inject constructor(
    private val validator: EmailValidator,
) {
    operator fun invoke(email: String): Boolean {
        return validator.isValid(email)
    }
}