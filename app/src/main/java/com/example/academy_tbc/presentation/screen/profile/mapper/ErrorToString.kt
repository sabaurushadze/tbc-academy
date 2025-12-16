package com.example.academy_tbc.presentation.screen.profile.mapper

import com.example.academy_tbc.R
import com.example.academy_tbc.domain.model.profile.ValidationError
import com.example.academy_tbc.presentation.common.text.GenericString

fun ValidationError.toGenericString(): GenericString {
    val stringRes = when (this) {
        ValidationError.USERNAME_EMPTY -> R.string.username_cannot_be_empty
        ValidationError.MAX_USERNAME_CHAR -> R.string.username_cannot_exceed_22_characters
        ValidationError.MIN_USERNAME_CHAR -> R.string.username_must_be_at_least_3_characters
        ValidationError.UNKNOWN -> R.string.unknown_error
    }
    return GenericString.StringResource(stringRes)
}