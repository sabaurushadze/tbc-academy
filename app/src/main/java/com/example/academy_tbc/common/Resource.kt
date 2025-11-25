package com.example.academy_tbc.common

sealed class Resource<out T> {
    data class Success<out T>(val data: T) : Resource<T>()
    data class Error(val exception: AppException) : Resource<Nothing>()
    data class Loading(val isLoading: Boolean) : Resource<Nothing>()
}