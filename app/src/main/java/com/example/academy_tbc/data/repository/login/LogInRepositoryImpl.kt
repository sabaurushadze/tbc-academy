package com.example.academy_tbc.data.repository.login

import com.example.academy_tbc.data.common.ResponseHandler
import com.example.academy_tbc.data.mapper.resource.asResource
import com.example.academy_tbc.data.mapper.network.toDomain
import com.example.academy_tbc.data.model.request.login.LogInRequestDto
import com.example.academy_tbc.data.service.login.LogInApiService
import com.example.academy_tbc.domain.model.login.LogInResponse
import com.example.academy_tbc.domain.repository.login.LogInRepository
import com.example.academy_tbc.domain.resource.Resource
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class LogInRepositoryImpl @Inject constructor(
    private val responseHandler: ResponseHandler,
    private val authService: LogInApiService,
) : LogInRepository {
    override fun logIn(
        email: String,
        password: String,
        rememberMe: Boolean,
    ): Flow<Resource<LogInResponse>> {
        return responseHandler.safeApiCall {
            authService.login(LogInRequestDto(email = email, password = password))
        }.asResource { it.toDomain() }
    }
}
