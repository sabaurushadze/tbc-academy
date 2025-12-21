package com.example.academy_tbc.domain.model.auth.sign_up

import com.example.academy_tbc.domain.common.ResourceError

enum class SignUpValidationError : ResourceError {
    FIRST_NAME_EMPTY,
    LAST_NAME_EMPTY,
    EMAIL_EMPTY,
    EMAIL_WRONG_FORMAT,
    PASSWORD_EMPTY,
    PASSWORD_WRONG_FORMAT,
    WRONG_PHONE_NUMBER_FORMAT,
    WRONG_OTP_FORMAT,
    DEPARTMENT_NOT_SELECTED,
    CONFIRM_PASSWORD_EMPTY,
    TOS_NOT_SELECTED,
    CONFIRM_PASSWORD_MISMATCH,
    VALID,
}