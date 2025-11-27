package com.example.academy_tbc.presentation.screen.admin

import android.util.Patterns
import javax.inject.Inject

class AdminValidations @Inject constructor() {
    fun validateEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches() && email.isNotBlank()
    }

    fun validateFirstName(firstName: String): Boolean {
        return firstName.isNotBlank()
    }

    fun validateLastName(lastName: String): Boolean {
        return lastName.isNotBlank()
    }

    fun validateAll(firstName: String, lastName: String, email: String): Boolean {
        return (validateEmail(email) && validateFirstName(firstName) && validateLastName(lastName))
    }
}