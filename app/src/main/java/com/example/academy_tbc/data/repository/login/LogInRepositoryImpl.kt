package com.example.academy_tbc.data.repository.login

import com.example.academy_tbc.data.common.ApiResponseHandler
import com.example.academy_tbc.data.common.mapResource
import com.example.academy_tbc.data.remote.mapper.toDomain
import com.example.academy_tbc.data.remote.service.login.LogInApiService
import com.example.academy_tbc.domain.common.Resource
import com.example.academy_tbc.domain.model.login.LogInResponse
import com.example.academy_tbc.domain.repository.login.LogInRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LogInRepositoryImpl @Inject constructor(
    private val responseHandler: ApiResponseHandler,
    private val authService: LogInApiService,
) : LogInRepository {
    override fun logIn(): Flow<Resource<LogInResponse>> {
        return responseHandler.safeApiCall {
            authService.login()
        }.mapResource { it.toDomain() }
    }
}
