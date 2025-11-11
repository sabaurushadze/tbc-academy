package com.example.academy_tbc.utils

import android.util.Patterns

object Validations {
    fun validateEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches() && email.isNotBlank()
    }

    fun validatePassword(password: String): Boolean {
        return password.isNotBlank()
    }

    fun validateUserName(userName: String): Boolean {
        return userName.isNotBlank()
    }
}