package com.example.academy_tbc.data.common

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

object ResponseHandler {
    fun <T : Any> safeApiCall(call: suspend () -> Response<T>) = flow {
        emit(Result.Loading(isLoading = true))

        try {
            val response = call()

            if (response.isSuccessful) {
                response.body()?.let {
                    emit(Result.Success(it))
                } ?: emit(Result.Error("Empty body"))
            } else {
                val errorMsg = response.errorBody()?.string().orEmpty()
                emit(Result.Error(errorMessage = errorMsg))
            }

        } catch (e: IOException) {
            emit(Result.Error(errorMessage = e.message ?: ""))
        } catch (e: HttpException) {
            emit(Result.Error(errorMessage = e.message ?: ""))
        } catch (e: IllegalStateException) {
            emit(Result.Error(errorMessage = e.message ?: ""))
        } catch (e: Throwable) {
            emit(Result.Error(errorMessage = e.message ?: ""))
        }
        emit(Result.Loading(isLoading = false))
    }.flowOn(Dispatchers.IO)
}