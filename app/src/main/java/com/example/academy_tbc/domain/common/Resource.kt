package com.example.academy_tbc.domain.common

sealed interface Resource<out D, out E : ResourceError> {
    data class Success<out D>(val data: D) : Resource<D, Nothing>
    data class Error<out E : ResourceError>(val error: E) : Resource<Nothing, E>
    data object Loading : Resource<Nothing, Nothing>
}