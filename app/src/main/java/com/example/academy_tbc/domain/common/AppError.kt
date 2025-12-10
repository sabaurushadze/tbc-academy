package com.example.academy_tbc.domain.common

sealed class AppError {
    data object Network : AppError()
    data class Server(val code: Int) : AppError()
    data object Unknown : AppError()
}

// globalurad gvqondes, error mapperebi (sealed class jobia)