package com.example.academy_tbc.data.common

import androidx.annotation.StringRes

sealed class AppException : Throwable() {
    data class ErrorRes(@StringRes val errorRes: Int) : AppException()
    data class ServerError(val errorCode: Int) : AppException()
}