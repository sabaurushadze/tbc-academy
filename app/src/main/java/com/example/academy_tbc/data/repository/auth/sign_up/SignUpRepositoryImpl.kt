package com.example.academy_tbc.data.repository.auth.sign_up

import com.example.academy_tbc.data.common.ApiResponseHandler
import com.example.academy_tbc.data.dto.request.auth.sign_up.SignUpRequestDto
import com.example.academy_tbc.data.service.auth.sign_up.SignUpService
import com.example.academy_tbc.domain.common.ApiError
import com.example.academy_tbc.domain.common.Resource
import com.example.academy_tbc.domain.repository.auth.sign_up.SignUpRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SignUpRepositoryImpl @Inject constructor(
    private val api: SignUpService,
    private val responseHandler: ApiResponseHandler
) : SignUpRepository {
    override fun signUp(
        firstName: String,
        lastName: String,
        email: String,
        phoneNumber: String,
        department: Int,
        password: String,
    ): Flow<Resource<Unit, ApiError>> {
        return responseHandler.safeApiCall {
            api.signUp(SignUpRequestDto(
                firstName = firstName,
                lastName = lastName,
                email = email,
                phoneNumber = phoneNumber,
                departmentId = department,
                password = password
            ))
        }
    }
}