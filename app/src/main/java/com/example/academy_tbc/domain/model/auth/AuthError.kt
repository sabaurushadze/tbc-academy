package com.example.academy_tbc.domain.model.auth

import com.example.academy_tbc.domain.common.ResourceError

enum class AuthError : ResourceError {
    UNKNOWN,
    INVALID_CREDENTIALS,
    NETWORK_ERROR,
    INVALID_EMAIL_FORMAT,
    USER_COLLISION
}