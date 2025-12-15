package com.example.academy_tbc.domain.usecase.auth

import com.example.academy_tbc.domain.model.auth.User
import com.example.academy_tbc.domain.repository.auth.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCurrentUserUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {
    operator fun invoke(): Flow<User?> {
        return authRepository.currentUser
    }
}