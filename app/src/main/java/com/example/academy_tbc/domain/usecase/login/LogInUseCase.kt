package com.example.academy_tbc.domain.usecase.login

import com.example.academy_tbc.domain.common.ApiError
import com.example.academy_tbc.domain.model.login.LogInResponse
import com.example.academy_tbc.domain.repository.login.LogInRepository
import com.example.academy_tbc.domain.common.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LogInUseCase @Inject constructor(
    private val logInRepository: LogInRepository,
) {
    operator fun invoke(
        email: String, password: String
    ): Flow<Resource<LogInResponse, ApiError>> {
        return logInRepository.logIn()
    }
}