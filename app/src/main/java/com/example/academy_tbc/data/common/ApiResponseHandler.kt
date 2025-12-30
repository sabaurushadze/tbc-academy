package com.example.academy_tbc.data.common

import com.example.academy_tbc.domain.common.ApiError
import com.example.academy_tbc.domain.common.BaseDto
import com.example.academy_tbc.domain.common.Resource
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

class ApiResponseHandler @Inject constructor() {
    fun <T : Any> safeApiCall(call: suspend () -> Response<T>) = flow {
        emit(Resource.Loading(true))

        try {
            val response = call()

            if (response.isSuccessful) {
                val body = response.body()
                body?.let {
                    emit(Resource.Success(it))
                }
            } else {
                val errorBodyString = response.errorBody()?.string()
                val serverMessage = errorBodyString?.let { jsonString ->
                    Json.decodeFromString<BaseDto<Any>>(jsonString).errorMessage
                }.orEmpty()
                emit(Resource.Error(serverError = serverMessage))
            }
        } catch (e: Exception) {
            val error = when (e) {
                is CancellationException -> throw e
                is SocketTimeoutException -> ApiError.Network.TIMEOUT
                is IOException -> ApiError.Network.NETWORK
                is SerializationException -> ApiError.Parsing.INVALID_JSON
                else -> ApiError.Http.UNKNOWN
            }
            emit(Resource.Error(error))
        }
        emit(Resource.Loading(false))

    }
}