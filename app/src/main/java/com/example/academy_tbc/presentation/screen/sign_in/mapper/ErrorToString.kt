package com.example.academy_tbc.presentation.screen.sign_in.mapper

import com.example.academy_tbc.R
import com.example.academy_tbc.domain.common.AuthError
import com.example.academy_tbc.presentation.common.message.GenericString

fun AuthError.toGenericString(): GenericString {
    val stringRes = when (this) {
        AuthError.UNKNOWN -> R.string.unknown_error
        AuthError.INVALID_CREDENTIALS -> R.string.invalid_credentials
        AuthError.NETWORK_ERROR -> R.string.network_error
        AuthError.USER_COLLISION -> R.string.user_already_exists
        AuthError.INVALID_EMAIL_FORMAT -> R.string.invalid_email_format
    }
    return GenericString.StringResource(stringRes)
}