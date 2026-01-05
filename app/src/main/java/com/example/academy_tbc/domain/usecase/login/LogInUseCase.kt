package com.example.academy_tbc.domain.usecase.login

import com.example.academy_tbc.domain.common.DataError
import com.example.academy_tbc.domain.common.Resource
import com.example.academy_tbc.domain.model.login.AuthToken
import com.example.academy_tbc.domain.repository.login.LogInRepository
import javax.inject.Inject

class LogInUseCase @Inject constructor(
    private val logInRepository: LogInRepository,
) {
    suspend operator fun invoke(email: String, password: String): Resource<AuthToken, DataError.Network> {
        return logInRepository.logIn()
    }
}