package com.example.academy_tbc.presentation.common.mapper

import com.example.academy_tbc.R
import com.example.academy_tbc.domain.common.ApiError
import com.example.academy_tbc.domain.common.Resource
import com.example.academy_tbc.domain.common.ResourceError
import com.example.academy_tbc.presentation.util.GenericString

fun ResourceError.toGenericString(): GenericString = when(this) {
    is ApiError.Network -> when (this) {
        ApiError.Network.NETWORK -> GenericString.StringResource(R.string.no_internet_connection)
        ApiError.Network.TIMEOUT -> GenericString.StringResource(R.string.timeout_error)
    }
    is ApiError.Http -> when (this) {
        ApiError.Http.UNAUTHORIZED -> GenericString.StringResource(R.string.unauthorized)
        ApiError.Http.FORBIDDEN -> GenericString.StringResource(R.string.forbidden)
        ApiError.Http.NOT_FOUND -> GenericString.StringResource(R.string.not_found)
        ApiError.Http.SERVER_ERROR -> GenericString.StringResource(R.string.server_error)
        ApiError.Http.UNKNOWN -> GenericString.StringResource(R.string.unknown_error)
    }
    is ApiError.Parsing -> GenericString.StringResource(R.string.parsing_error)
    else -> GenericString.StringResource(R.string.unknown_error)
}

fun Resource.Error.toGenericString(): GenericString {
    serverError?.let { return GenericString.DynamicString(it) }

    return error?.toGenericString() ?: GenericString.StringResource(R.string.unknown_error)
}