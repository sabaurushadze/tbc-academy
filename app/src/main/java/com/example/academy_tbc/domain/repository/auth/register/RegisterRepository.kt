package com.example.academy_tbc.domain.repository.auth.register

import com.example.academy_tbc.domain.common.DataError
import com.example.academy_tbc.domain.common.Resource

interface RegisterRepository {
    suspend fun register(email: String, password: String): Resource<Unit, DataError.Auth>
}