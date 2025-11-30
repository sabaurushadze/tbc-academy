package com.example.academy_tbc.presentation.common.mapper

import androidx.annotation.StringRes
import com.example.academy_tbc.R
import com.example.academy_tbc.domain.common.AppError

@StringRes
fun AppError.toMessage(): Int {
    return when (this) {
        is AppError.Network -> R.string.no_internet_connection_please_try_again
        is AppError.Server -> when (code) {
            400 -> R.string.user_with_this_email_cannot_be_registered
            else -> R.string.something_went_wrong_please_try_again
        }

        is AppError.Unknown -> R.string.something_went_wrong_please_try_again
    }
}