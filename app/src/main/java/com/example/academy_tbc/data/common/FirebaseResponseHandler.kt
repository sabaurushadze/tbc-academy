package com.example.academy_tbc.data.common

import com.example.academy_tbc.domain.common.Resource
import com.example.academy_tbc.domain.model.auth.AuthError
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

class FirebaseResponseHandler @Inject constructor() {

    fun <T> safeCall(call: suspend () -> T): Flow<Resource<T, AuthError>> {
        return flow {
            emit(Resource.Loading)
            emit(safeCallNoLoading(call = call))
        }
    }

    suspend fun <T> safeCallNoLoading(call: suspend () -> T): Resource<T, AuthError> {
        return withContext(Dispatchers.IO) {
            try {
                val result = call()
                Resource.Success(result)
            } catch (e: Exception) {
                val error = when (e) {
                    is CancellationException -> throw e

                    is FirebaseAuthInvalidCredentialsException -> AuthError.INVALID_CREDENTIALS

                    is FirebaseNetworkException -> AuthError.NETWORK_ERROR

                    is FirebaseAuthUserCollisionException -> AuthError.USER_COLLISION

                    else -> AuthError.UNKNOWN
                }
                Resource.Error(error)
            }
        }
    }
}