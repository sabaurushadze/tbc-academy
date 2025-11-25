package com.example.academy_tbc.common

import com.example.academy_tbc.data.manager.ConnectivityObserver
import jakarta.inject.Inject
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.Json
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException

class ResponseHandler @Inject constructor(
    private val connectivityObserver: ConnectivityObserver,
) {
    fun <T : Any> safeApiCall(call: suspend () -> Response<T>) = flow {
        emit(Resource.Loading(isLoading = true))

        try {
            val isConnected = connectivityObserver.isConnected.first()
            if (!isConnected) {
                emit(Resource.Error(AppException.Network(IOException())))
                return@flow
            }
            val response = call()

            if (response.isSuccessful) {
                response.body()?.let {
                    emit(Resource.Success(data = it))
                }
            } else {
                val errorMsgJson = response.errorBody()?.string().orEmpty()
                val errorResponse = Json.decodeFromString<ErrorResponse>(errorMsgJson)

                emit(
                    Resource.Error(
                        exception = AppException.Http(
                            code = response.code(), body = errorResponse.error
                        )
                    )
                )
            }

        } catch (e: Throwable) {
            val exception = when (e) {
                is SocketTimeoutException -> AppException.Timeout(e)
                is IOException -> AppException.Network(e)
                else -> AppException.Unknown(e)
            }
            emit(Resource.Error(exception = exception))
        } finally {
            emit(Resource.Loading(isLoading = false))
        }
    }
}