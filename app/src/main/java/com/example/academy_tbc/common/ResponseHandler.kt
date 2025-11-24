package com.example.academy_tbc.common

import jakarta.inject.Inject
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.Json
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

class ResponseHandler @Inject constructor() {
    fun <T : Any> safeApiCall(call: suspend () -> Response<T>) = flow {
        emit(Resource.Loading(isLoading = true))

        try {
            val response = call()

            if (response.isSuccessful) {
                response.body()?.let {
                    emit(Resource.Success(data = it))
                }
            } else {
                val errorMsgJson = response.errorBody()?.string().orEmpty()
                val errorMsg = Json.decodeFromString<ErrorMessage>(errorMsgJson)
                emit(Resource.Error(errorMessage = errorMsg.error))
            }

        } catch (e: Throwable) {
            val message = when (e) {
                is IOException -> e.message
                is HttpException -> e.message
                is IllegalStateException -> e.message
                else -> e.message
            }
            emit(Resource.Error(errorMessage = message.orEmpty()))
        } finally {
            emit(Resource.Loading(isLoading = false))
        }
    }
}