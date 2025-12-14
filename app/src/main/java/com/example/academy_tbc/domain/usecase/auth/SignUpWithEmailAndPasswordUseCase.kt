package com.example.academy_tbc.domain.usecase.auth

import com.example.academy_tbc.domain.common.AuthError
import com.example.academy_tbc.domain.common.Resource
import com.example.academy_tbc.domain.repository.auth.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SignUpWithEmailAndPasswordUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {
    operator fun invoke(email: String, password: String): Flow<Resource<Unit, AuthError>> {
        return authRepository.signUp(email = email, password = password)
    }
}