package com.example.academy_tbc.domain.usecase.register

import com.example.academy_tbc.domain.model.register.RegisterResponse
import com.example.academy_tbc.domain.repository.register.RegisterRepository
import com.example.academy_tbc.domain.resource.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val registerRepository: RegisterRepository,
) {
    operator fun invoke(
        email: String, password: String
    ): Flow<Resource<RegisterResponse>> {
        return registerRepository.register(
            email = email, password = password
        )
    }
}