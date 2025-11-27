package com.example.academy_tbc.data.common

import com.example.academy_tbc.R
import jakarta.inject.Inject
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException

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
                emit(
                    Resource.ServerError(
                        errorCode = response.code()
                    )
                )
            }

        } catch (e: Throwable) {
            val errorRes = when (e) {
                is SocketTimeoutException -> R.string.timeout
                is IOException -> R.string.no_internet_connection_please_try_again
                else -> R.string.something_went_wrong_please_try_again
            }
            emit(Resource.Error(errorRes = errorRes))
        } finally {
            emit(Resource.Loading(isLoading = false))
        }
    }
}