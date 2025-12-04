package com.example.academy_tbc.domain.common

sealed class Resource<out T> {
    data class Success<out T>(val data: T) : Resource<T>()
    data class Error(val error: AppError) : Resource<Nothing>()
    data class Loading(val isLoading: Boolean) : Resource<Nothing>()
}