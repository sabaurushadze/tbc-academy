package com.example.academy_tbc.utils

import android.content.Context
import android.util.Patterns
import android.widget.EditText
import com.example.academy_tbc.R

object Validations {
    fun isValidInput(
        context: Context,
        etFirstName: EditText,
        etLastName: EditText,
        etAge: EditText,
        firstName: String,
        lastName: String,
        age: String,
    ): Boolean {

        fun setError(view: EditText, message: String): Boolean {
            view.error = message
            return false
        }
        return when {
            firstName.isBlank() -> setError(
                etFirstName, context.getString(R.string.error_please_enter_your_first_name)
            )

            lastName.isBlank() -> setError(
                etLastName, context.getString(R.string.error_please_enter_your_last_name)
            )

            age.isBlank() -> setError(
                etAge, context.getString(R.string.error_please_enter_your_age)
            )

            firstName.length !in 2..30 -> setError(
                etFirstName, context.getString(R.string.error_first_name_length_is_not_valid)
            )

            lastName.length !in 2..30 -> setError(
                etLastName, context.getString(R.string.error_last_name_length_is_not_valid)
            )

            age.toInt() !in 1..120 -> setError(
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

        fun setError(view: EditText, message: String): Boolean {
            view.error = message
            return false
        }
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