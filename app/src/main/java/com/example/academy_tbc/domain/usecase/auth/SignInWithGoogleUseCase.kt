package com.example.academy_tbc.domain.usecase.auth

import com.example.academy_tbc.domain.model.auth.AuthError
import com.example.academy_tbc.domain.common.Resource
import com.example.academy_tbc.domain.repository.auth.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SignInWithGoogleUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {
    operator fun invoke(idToken: String): Flow<Resource<Unit, AuthError>> {
        return authRepository.signInWithGoogle(idToken)
    }
}