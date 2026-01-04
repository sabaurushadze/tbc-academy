package com.example.academy_tbc.domain.common

enum class ApiError : Failure {
    UNKNOWN,
    NETWORK_ERROR,
    TIMEOUT,
    SERIALIZATION
}