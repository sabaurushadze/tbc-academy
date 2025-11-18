package com.example.academy_tbc.data.common

sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val errorMessage: String) : Result<Nothing>()
    data class Loading(val isLoading: Boolean) : Result<Nothing>()
}