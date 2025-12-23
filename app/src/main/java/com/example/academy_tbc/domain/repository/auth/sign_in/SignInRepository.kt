package com.example.academy_tbc.domain.repository.auth.sign_in

import com.example.academy_tbc.domain.common.ApiError
import com.example.academy_tbc.domain.common.Resource
import com.example.academy_tbc.domain.common.ResourceError
import com.example.academy_tbc.domain.model.auth.sign_in.AuthToken
import kotlinx.coroutines.flow.Flow

interface SignInRepository {
    fun signIn(email: String, password: String): Flow<Resource<AuthToken, ApiError>>
}