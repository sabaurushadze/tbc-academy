package com.example.academy_tbc.domain.repository.login

import com.example.academy_tbc.domain.common.DataError
import com.example.academy_tbc.domain.common.Resource
import com.example.academy_tbc.domain.model.login.AuthToken

interface LogInRepository {
    suspend fun logIn(): Resource<AuthToken, DataError.Network>
}