package com.example.academy_tbc.domain.repository.register

import com.example.academy_tbc.domain.model.register.RegisterResponse
import com.example.academy_tbc.domain.resource.Resource
import kotlinx.coroutines.flow.Flow

interface RegisterRepository {
    fun register(email: String, password: String): Flow<Resource<RegisterResponse>>
}