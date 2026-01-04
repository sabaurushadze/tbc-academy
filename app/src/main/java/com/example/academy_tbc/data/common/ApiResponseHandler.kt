package com.example.academy_tbc.data.common

import com.example.academy_tbc.domain.common.ApiError
import com.example.academy_tbc.domain.common.Resource
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.SerializationException
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

class ApiResponseHandler @Inject constructor() {
    fun <T : Any> safeApiCall(call: suspend () -> Response<T>) = flow {
        emit(Resource.Loading)

        try {
            val response = call()

            if (response.isSuccessful) {
                val body = response.body()
                body?.let {
                    emit(Resource.Success(it))
                }
            }
        } catch (e: Exception) {
            val error = when (e) {
                is CancellationException -> throw e
                is SocketTimeoutException -> ApiError.TIMEOUT
                is IOException -> ApiError.NETWORK_ERROR
                is SerializationException -> ApiError.SERIALIZATION
                else -> ApiError.UNKNOWN
            }
            emit(Resource.Error(error))
        }
    }
}