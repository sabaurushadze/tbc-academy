package com.example.academy_tbc.domain.usecase.auth

import com.example.academy_tbc.domain.repository.auth.AuthRepository
import javax.inject.Inject

class HasUserUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {
    operator fun invoke(): Boolean {
        return authRepository.hasUser()
    }
}