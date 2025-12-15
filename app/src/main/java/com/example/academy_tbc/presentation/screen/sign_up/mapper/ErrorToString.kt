package com.example.academy_tbc.presentation.screen.sign_up.mapper

import com.example.academy_tbc.R
import com.example.academy_tbc.domain.model.auth.AuthValidationError
import com.example.academy_tbc.presentation.common.message.GenericString

fun AuthValidationError.toGenericString(): GenericString {
    val stringRes = when (this) {
        AuthValidationError.EMAIL_EMPTY -> R.string.email_is_empty
        AuthValidationError.PASSWORD_EMPTY -> R.string.password_is_empty
        AuthValidationError.EMAIL_WRONG_FORMAT -> R.string.wrong_email_format
        AuthValidationError.EMAIL_TOO_SHORT -> R.string.email_must_be_at_least_6_characters
        AuthValidationError.PASSWORD_TOO_SHORT -> R.string.password_must_be_at_least_6_characters
        AuthValidationError.EMAIL_TOO_LONG -> R.string.email_must_be_less_than_28_characters
        AuthValidationError.PASSWORD_TOO_LONG -> R.string.password_must_be_less_than_28_characters
        AuthValidationError.UNKNOWN -> R.string.something_went_wrong_please_try_again
    }
    return GenericString.StringResource(stringRes)
}