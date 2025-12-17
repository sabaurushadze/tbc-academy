package com.example.academy_tbc.presentation.common.mapper

import com.example.academy_tbc.R
import com.example.academy_tbc.domain.common.ApiError
import com.example.academy_tbc.presentation.util.GenericString

fun ApiError.toGenericString(): GenericString {
    val stringRes = when (this) {
        ApiError.UNKNOWN -> R.string.unknown_error
        ApiError.NETWORK_ERROR -> R.string.no_internet_connection
    }
    return GenericString.StringResource(stringRes)
}