package com.example.academy_tbc.utils

import android.content.Context
import android.util.Patterns
import android.widget.EditText
import com.example.academy_tbc.R

object Validations {
    private fun setError(view: EditText, message: String): Boolean {
        view.error = message
        return false
    }

    fun isValidInput(
        context: Context,
        etFirstName: EditText,
        etLastName: EditText,
        etAge: EditText,
        firstName: String,
        lastName: String,
        age: Int?,
    ): Boolean {

        return when {
            firstName.isBlank() -> setError(
                etFirstName, context.getString(R.string.error_please_enter_your_first_name)
            )

            lastName.isBlank() -> setError(
                etLastName, context.getString(R.string.error_please_enter_your_last_name)
            )

            age == null -> setError(
                etAge, context.getString(R.string.error_please_enter_your_age)
            )

            firstName.length !in 2..30 -> setError(
                etFirstName, context.getString(R.string.error_first_name_length_is_not_valid)
            )

            lastName.length !in 2..30 -> setError(
                etLastName, context.getString(R.string.error_last_name_length_is_not_valid)
            )

            age !in 1..120 -> setError(
                etAge, context.getString(R.string.error_please_enter_your_real_age)
            )

            else -> true
        }
    }

    fun isValidEmail(
        context: Context,
        etEmail: EditText,
        email: String,
    ): Boolean {

        return when {
            email.isBlank() -> setError(
                etEmail, context.getString(R.string.error_please_enter_your_email)
            )

            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> setError(
                etEmail, context.getString(R.string.error_invalid_email_format)
            )

            else -> true
        }
    }
}