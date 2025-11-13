package com.example.academy_tbc.presentation.screen.login

enum class LogInFieldErrors {
    INVALID_EMAIL,
    INVALID_PASSWORD,
}

enum class LogInExceptionErrors {
    EXCEPTION_NETWORK,
    EXCEPTION_CREDENTIALS,
    EXCEPTION_USER_NOT_FOUND,
    EXCEPTION_UNKNOWN
}

enum class LogInField {
    EMAIL,
    PASSWORD
}