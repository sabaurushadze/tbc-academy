package com.example.academy_tbc.data.common

import com.example.academy_tbc.domain.common.AppError
import com.example.academy_tbc.domain.common.Resource
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException
import javax.inject.Inject

class ResponseHandler @Inject constructor() {
    fun <T : Any> safeApiCall(call: suspend () -> Response<T>) = flow {
        emit(Resource.Loading(true))

        try {
            val response = call()

            if (response.isSuccessful) {
                response.body()?.let {
                    emit(Resource.Success(it))
                }
            } else {
                emit(Resource.Error(AppError.Server(response.code())))
            }

        } catch (e: Exception) {
            val appError = when (e) {
                is SocketTimeoutException -> AppError.Network
                is IOException -> AppError.Network
                else -> AppError.Unknown
            }
            emit(Resource.Error(appError))
        }

        emit(Resource.Loading(false))
    }
}