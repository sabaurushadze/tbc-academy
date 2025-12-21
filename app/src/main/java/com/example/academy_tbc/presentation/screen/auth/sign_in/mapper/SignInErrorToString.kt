package com.example.academy_tbc.presentation.screen.auth.sign_in.mapper

import com.example.academy_tbc.R
import com.example.academy_tbc.domain.model.auth.sign_in.SignInValidationError
import com.example.academy_tbc.presentation.util.GenericString

fun SignInValidationError.toGenericString(): GenericString {
    val stringRes = when (this) {
        SignInValidationError.EMAIL_EMPTY -> R.string.email_is_empty
        SignInValidationError.PASSWORD_EMPTY -> R.string.password_is_empty
        SignInValidationError.EMAIL_WRONG_FORMAT -> R.string.wrong_email_format
        SignInValidationError.VALID -> R.string.valid
    }
    return GenericString.StringResource(stringRes)
}