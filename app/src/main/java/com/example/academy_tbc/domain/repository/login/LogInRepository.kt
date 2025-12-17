package com.example.academy_tbc.domain.repository.login

import com.example.academy_tbc.domain.common.ApiError
import com.example.academy_tbc.domain.model.login.LogInResponse
import com.example.academy_tbc.domain.common.Resource
import kotlinx.coroutines.flow.Flow

interface LogInRepository {
    fun logIn(): Flow<Resource<LogInResponse, ApiError>>
}