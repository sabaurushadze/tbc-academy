package com.example.academy_tbc.domain.usecase.auth

import com.example.academy_tbc.domain.common.Resource
import com.example.academy_tbc.domain.model.auth.AuthError
import com.example.academy_tbc.domain.repository.auth.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SignOutUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {
    operator fun invoke(): Flow<Resource<Unit, AuthError>> {
        return authRepository.signOut()
    }
}