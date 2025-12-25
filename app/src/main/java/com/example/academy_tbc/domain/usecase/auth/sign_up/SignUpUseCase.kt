package com.example.academy_tbc.domain.usecase.auth.sign_up

import com.example.academy_tbc.domain.common.ApiError
import com.example.academy_tbc.domain.common.Resource
import com.example.academy_tbc.domain.repository.auth.sign_up.SignUpRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val signUpRepository: SignUpRepository,
) {
    operator fun invoke(
        firstName: String,
        lastName: String,
        email: String,
        phoneNumber: String,
        department: Int,
        password: String,
    ): Flow<Resource<Unit, ApiError>> {
        return signUpRepository.signUp(
            firstName = firstName,
            lastName = lastName,
            email = email,
            phoneNumber = phoneNumber,
            department = department,
            password = password
        )
    }
}