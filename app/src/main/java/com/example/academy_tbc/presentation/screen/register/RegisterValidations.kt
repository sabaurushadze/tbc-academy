package com.example.academy_tbc.presentation.screen.register

enum class RegisterFieldErrors {
    INVALID_EMAIL, INVALID_PASSWORD, INVALID_USERNAME
}

enum class RegisterExceptionErrors {
    EXCEPTION_NETWORK,
    EXCEPTION_CREDENTIALS,
    EXCEPTION_USER_NOT_FOUND,
    EXCEPTION_UNKNOWN
}

enum class RegisterField {
    EMAIL, PASSWORD, USERNAME
}