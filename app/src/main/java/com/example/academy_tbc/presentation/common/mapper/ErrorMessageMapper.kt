package com.example.academy_tbc.presentation.common.mapper

import androidx.annotation.StringRes
import com.example.academy_tbc.R
import com.example.academy_tbc.domain.common.AppError

@StringRes
fun AppError.toMessage(): Int {
    return when (this) {
        is AppError.Network -> R.string.no_internet_connection_please_try_again
        is AppError.Server -> when (code) {
            404 -> R.string.service_is_currently_unavailable
            else -> R.string.something_went_wrong_please_try_again
        }

        is AppError.Unknown -> R.string.something_went_wrong_please_try_again
    }
}