package com.example.academy_tbc.domain.usecase.auth.register

import com.example.academy_tbc.domain.common.DataError
import com.example.academy_tbc.domain.common.Resource
import com.example.academy_tbc.domain.repository.auth.register.RegisterRepository
import javax.inject.Inject

class RegisterWithEmailAndPasswordUseCase @Inject constructor(
    private val registerRepository: RegisterRepository
) {
    suspend operator fun invoke(email: String, password: String): Resource<Unit, DataError.Auth> {
        return registerRepository.register(email = email, password = password)
    }
}