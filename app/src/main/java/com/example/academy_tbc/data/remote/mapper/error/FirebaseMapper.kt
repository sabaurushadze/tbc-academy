package com.example.academy_tbc.data.remote.mapper.error

import com.example.academy_tbc.domain.common.DataError
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import kotlinx.coroutines.CancellationException

fun mapExceptionToSignInError(e: Exception): DataError.Auth {
    return when (e) {
        is FirebaseAuthUserCollisionException -> DataError.Auth.AccountAlreadyExists
        is CancellationException -> DataError.Auth.Cancelled
        else -> DataError.Auth.Unknown
    }
}