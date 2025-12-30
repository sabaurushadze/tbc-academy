package com.example.academy_tbc.domain.common

sealed interface Resource<out T> {
    data class Success<out T>(val data: T) : Resource<T>
    data class Error(
        val error: ResourceError? = null,
        val serverError: String? = null
    ) : Resource<Nothing>

    data class Loading(val isLoading: Boolean) : Resource<Nothing>
}