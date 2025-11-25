package com.example.academy_tbc.common

import com.example.academy_tbc.R

fun AppException.toMessageRes(): Int {
    return when (this) {
        is AppException.Network -> R.string.no_internet
        is AppException.Timeout -> R.string.timeout
        is AppException.Http -> when (this.code) {
            400 -> R.string.user_not_found
            in 500..599 -> R.string.something_went_wrong_please_try_again
            else -> R.string.something_went_wrong_please_try_again
        }
        is AppException.Unknown -> R.string.something_went_wrong_please_try_again
    }
}