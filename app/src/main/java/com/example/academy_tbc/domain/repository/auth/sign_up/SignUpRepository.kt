package com.example.academy_tbc.domain.repository.auth.sign_up

import com.example.academy_tbc.domain.common.ApiError
import com.example.academy_tbc.domain.common.Resource
import kotlinx.coroutines.flow.Flow

interface SignUpRepository {
    fun signUp(
        firstName: String,
        lastName: String,
        email: String,
        phoneNumber: String,
        department: Int,
        password: String,
    ): Flow<Resource<Unit, ApiError>>
}