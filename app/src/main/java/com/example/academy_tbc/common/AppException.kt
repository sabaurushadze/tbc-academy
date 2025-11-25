package com.example.academy_tbc.common

sealed class AppException : Throwable() {
    data class Network(val exception: Throwable? = null) : AppException()
    data class Http(val code: Int, val body: String? = null) : AppException()
    data class Timeout(val exception: Throwable? = null) : AppException()
    data class Unknown(val exception: Throwable? = null) : AppException()
}