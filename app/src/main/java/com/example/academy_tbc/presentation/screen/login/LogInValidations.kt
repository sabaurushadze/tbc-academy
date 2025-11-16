package com.example.academy_tbc.presentation.screen.login

import android.util.Patterns

object LogInValidations {
    fun validateEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches() && email.isNotBlank()
    }

    fun validatePassword(password: String): Boolean {
        return password.isNotBlank()
    }
}