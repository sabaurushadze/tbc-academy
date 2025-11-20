package com.example.academy_tbc.data.common

import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
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

        } catch (e: IOException) {
            emit(Resource.Error(errorMessage = e.message.orEmpty()))
        } catch (e: HttpException) {
            emit(Resource.Error(errorMessage = e.message.orEmpty()))
        } catch (e: IllegalStateException) {
            emit(Resource.Error(errorMessage = e.message.orEmpty()))
        } catch (e: Throwable) {
            emit(Resource.Error(errorMessage = e.message.orEmpty()))
        }
        emit(Resource.Loading(isLoading = false))
    }.flowOn(Dispatchers.IO)
}