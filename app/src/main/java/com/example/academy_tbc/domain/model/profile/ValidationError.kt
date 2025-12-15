package com.example.academy_tbc.domain.model.profile

import com.example.academy_tbc.domain.common.ResourceError

enum class ValidationError : ResourceError {
    USERNAME_EMPTY,
    MIN_USERNAME_CHAR,
    MAX_USERNAME_CHAR,
    UNKNOWN;
}