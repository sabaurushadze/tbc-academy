package com.example.academy_tbc.data.repository.auth.sign_in

import com.example.academy_tbc.data.common.ApiResponseHandler
import com.example.academy_tbc.data.common.mapResource
import com.example.academy_tbc.data.dto.request.auth.sign_in.SignInRequestDto
import com.example.academy_tbc.data.mapper.auth.sign_in.toDomain
import com.example.academy_tbc.data.service.auth.sign_in.SignInService
import com.example.academy_tbc.domain.common.ApiError
import com.example.academy_tbc.domain.common.Resource
import com.example.academy_tbc.domain.model.auth.sign_in.AuthToken
import com.example.academy_tbc.domain.repository.auth.sign_in.SignInRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SignInRepositoryImpl @Inject constructor(
    private val api: SignInService,
    private val responseHandler: ApiResponseHandler,
) : SignInRepository {
    override fun signIn(
        email: String,
        password: String,
    ): Flow<Resource<AuthToken, ApiError>> {
        return responseHandler.safeApiCall {
            api.signIn(SignInRequestDto(email = email, password = password))
        }.mapResource {
            it.toDomain()
        }
    }
}