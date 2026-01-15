package com.example.academy_tbc.domain.repository.auth.login

import com.example.academy_tbc.domain.common.DataError
import com.example.academy_tbc.domain.common.Resource

interface LogInRepository {
    suspend fun logIn(email: String, password: String): Resource<Unit, DataError.Auth>
}