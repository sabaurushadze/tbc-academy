package com.example.academy_tbc.domain.usecase.auth.login

import com.example.academy_tbc.domain.common.DataError
import com.example.academy_tbc.domain.common.Resource
import com.example.academy_tbc.domain.repository.auth.login.LogInRepository
import javax.inject.Inject

class LogInWithEmailAndPasswordUseCase @Inject constructor(
    private val logInRepository: LogInRepository
) {
    suspend operator fun invoke(email: String, password: String): Resource<Unit, DataError.Auth> {
        return logInRepository.logIn(email = email, password = password)
    }
}