package com.example.academy_tbc.domain.model.auth

import com.example.academy_tbc.domain.common.ResourceError

enum class AuthValidationError : ResourceError {
    EMAIL_EMPTY,
    PASSWORD_EMPTY,
    EMAIL_WRONG_FORMAT,
    EMAIL_TOO_SHORT,
    PASSWORD_TOO_SHORT,
    EMAIL_TOO_LONG,
    PASSWORD_TOO_LONG,
    UNKNOWN,
}