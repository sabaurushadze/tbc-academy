package com.example.academy_tbc.domain.common

sealed interface ApiError : ResourceError {
    enum class Network : ApiError { NETWORK, TIMEOUT }
    enum class Http : ApiError { UNAUTHORIZED, FORBIDDEN, NOT_FOUND, SERVER_ERROR, UNKNOWN }
    enum class Parsing : ApiError { INVALID_JSON }
}