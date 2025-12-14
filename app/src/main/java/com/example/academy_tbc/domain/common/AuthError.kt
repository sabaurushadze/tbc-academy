package com.example.academy_tbc.domain.common

enum class AuthError : ResourceError {
    UNKNOWN,
    INVALID_CREDENTIALS,
    NETWORK_ERROR,
    INVALID_EMAIL_FORMAT,
    USER_COLLISION
}