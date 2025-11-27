package com.example.academy_tbc.data.common

import androidx.annotation.StringRes

sealed class Resource<out T> {
    data class Success<out T>(val data: T) : Resource<T>()
    data class Error(@StringRes val errorRes: Int) : Resource<Nothing>()
    data class ServerError(val errorCode: Int) : Resource<Nothing>()
    data class Loading(val isLoading: Boolean) : Resource<Nothing>()
}