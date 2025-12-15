package com.example.academy_tbc.data.common

import android.util.Log.d
import com.example.academy_tbc.domain.common.ApiError
import com.example.academy_tbc.domain.common.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.net.UnknownHostException
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

class ResponseHandler @Inject constructor() {
    fun <T> safeCall(call: suspend () -> Response<T>): Flow<Resource<T, ApiError>> {
        return flow {
            emit(Resource.Loading)
            emit(safeCallNoLoading(call = call))
        }

    }

    suspend fun <T> safeCallNoLoading(call: suspend () -> Response<T>): Resource<T, ApiError> {
        return withContext(Dispatchers.IO) {
            try {
                val response = call()
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        Resource.Success(body)
                    } else {
                        d("asdd", "called in first")
                        Resource.Error(ApiError.UNKNOWN)
                    }
                } else {
                    val errorBodyString = response.errorBody()?.string()
                    if (errorBodyString.isNullOrEmpty()) {
                        d("asdd", "called in second")
                        Resource.Error(ApiError.UNKNOWN)

                    } else {
                        d("asdd", "called in third")
                        Resource.Error(
                            ApiError.valueOf(
                                ApiError.UNKNOWN.name
                            )
                        )

                    }
                }
            } catch (e: Exception) {
                when (e) {
                    is CancellationException -> {
                        throw e
                    }

                    is UnknownHostException -> {
                        Resource.Error(ApiError.NETWORK_ERROR)
                    }

                    else -> {

                        d("asdd", "${e.localizedMessage}")
                        Resource.Error(ApiError.UNKNOWN)
                    }
                }

            }

        }
    }
}
