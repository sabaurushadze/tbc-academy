package com.example.academy_tbc.presentation.screen.home.category.mapper

import com.example.academy_tbc.R
import com.example.academy_tbc.domain.common.ApiError
import com.example.academy_tbc.presentation.common.text.GenericString

fun ApiError.toGenericString(): GenericString {
    val stringRes = when (this) {
        ApiError.UNKNOWN -> R.string.unknown_error
        ApiError.NETWORK_ERROR -> R.string.network_error
    }
    return GenericString.StringResource(stringRes)
}