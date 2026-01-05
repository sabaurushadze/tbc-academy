package com.example.academy_tbc.data.repository.login

import com.example.academy_tbc.data.common.ApiResponseHandler
import com.example.academy_tbc.data.remote.mapper.LogInResponseDtoMapper
import com.example.academy_tbc.data.remote.service.login.LogInApiService
import com.example.academy_tbc.domain.common.DataError
import com.example.academy_tbc.domain.common.Resource
import com.example.academy_tbc.domain.common.map
import com.example.academy_tbc.domain.model.login.AuthToken
import com.example.academy_tbc.domain.repository.login.LogInRepository
import javax.inject.Inject

class LogInRepositoryImpl @Inject constructor(
    private val responseHandler: ApiResponseHandler,
    private val authService: LogInApiService,
    private val logInResponseDtoMapper: LogInResponseDtoMapper,
) : LogInRepository {
    override suspend fun logIn(): Resource<AuthToken, DataError.Network> {
        return responseHandler.safeApiCall {
            authService.login()
        }.map(logInResponseDtoMapper::mapToDomain)
    }
}
