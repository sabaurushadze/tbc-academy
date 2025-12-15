package com.example.academy_tbc.domain.usecase.profile

import com.example.academy_tbc.domain.model.profile.ValidationError
import javax.inject.Inject

class ValidateUserNameUseCase @Inject constructor (){
    operator fun invoke(userName: String): ValidationError {
        return when {
            userName.isBlank() -> ValidationError.USERNAME_EMPTY
            userName.length < 3 -> ValidationError.MIN_USERNAME_CHAR
            userName.length > 22 -> ValidationError.MAX_USERNAME_CHAR
            else -> ValidationError.UNKNOWN
        }
    }
}