package com.example.academy_tbc.domain.repository.login

import com.example.academy_tbc.domain.common.DataError
import com.example.academy_tbc.domain.common.Resource
import com.example.academy_tbc.domain.model.login.AuthToken

interface LogInRepository {
    suspend fun logIn(email: String, password: String): Resource<AuthToken, DataError.Network>
}