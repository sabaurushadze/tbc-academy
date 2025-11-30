package com.example.academy_tbc.data.repository.register

import com.example.academy_tbc.data.common.ResponseHandler
import com.example.academy_tbc.data.mapper.resource.asResource
import com.example.academy_tbc.data.mapper.network.toDomain
import com.example.academy_tbc.data.model.request.register.RegisterRequestDto
import com.example.academy_tbc.data.service.register.RegisterApiService
import com.example.academy_tbc.domain.model.register.RegisterResponse
import com.example.academy_tbc.domain.repository.register.RegisterRepository
import com.example.academy_tbc.domain.resource.Resource
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class RegisterRepositoryImpl @Inject constructor(
    private val responseHandler: ResponseHandler,
    private val authService: RegisterApiService,
) : RegisterRepository {

    override fun register(email: String, password: String): Flow<Resource<RegisterResponse>> {
        return responseHandler.safeApiCall {
            authService.register(RegisterRequestDto(email = email, password = password))
        }.asResource { it.toDomain() }
    }
}