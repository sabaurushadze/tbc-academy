package com.example.academy_tbc.domain.usecase.validations

import javax.inject.Inject

class PasswordValidationUseCase @Inject constructor() {
    operator fun invoke(password: String): Boolean {
        return password.isNotBlank()
    }
}