package com.example.academy_tbc.presentation.validator

import android.util.Patterns
import com.example.academy_tbc.domain.validator.EmailValidator
import javax.inject.Inject

class AndroidEmailValidator @Inject constructor() : EmailValidator {
    override fun isValid(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches() && email.isNotBlank()
    }
}