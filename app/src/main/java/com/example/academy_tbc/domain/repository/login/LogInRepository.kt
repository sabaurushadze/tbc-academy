package com.example.academy_tbc.domain.repository.login

import com.example.academy_tbc.domain.model.login.LogInResponse
import com.example.academy_tbc.domain.resource.Resource
import kotlinx.coroutines.flow.Flow

interface LogInRepository {
    fun logIn(email: String, password: String): Flow<Resource<LogInResponse>>
}