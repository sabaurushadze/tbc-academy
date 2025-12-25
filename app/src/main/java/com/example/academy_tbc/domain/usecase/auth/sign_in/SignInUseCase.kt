package com.example.academy_tbc.domain.usecase.auth.sign_in

import com.example.academy_tbc.domain.common.ApiError
import com.example.academy_tbc.domain.common.Resource
import com.example.academy_tbc.domain.model.auth.sign_in.AuthToken
import com.example.academy_tbc.domain.repository.auth.sign_in.SignInRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    private val signInRepository: SignInRepository,
) {
    operator fun invoke(email: String, password: String): Flow<Resource<AuthToken, ApiError>> {
        return signInRepository.signIn(email = email, password = password)
    }
}