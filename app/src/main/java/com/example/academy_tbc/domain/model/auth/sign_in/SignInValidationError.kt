package com.example.academy_tbc.domain.model.auth.sign_in

import com.example.academy_tbc.domain.common.ResourceError

enum class SignInValidationError : ResourceError {
    EMAIL_EMPTY,
    PASSWORD_EMPTY,
    EMAIL_WRONG_FORMAT,
    VALID,
}