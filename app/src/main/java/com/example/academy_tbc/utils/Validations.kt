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
        etId: EditText,
        etFirstName: EditText,
        etLastName: EditText,
        etBirthday: EditText,
        etAddress: EditText,
        etEmail: EditText,
        id: String,
        firstName: String,
        lastName: String,
        birthday: String,
        address: String,
        email: String
    ): Boolean {

        return when {
            id.isBlank() -> setError(
                etId, context.getString(R.string.error_please_enter_an_id)
            )

            id.toInt() == 0 -> setError(
                etId, context.getString(R.string.error_invalid_id)
            )

            firstName.isBlank() -> setError(
                etFirstName, context.getString(R.string.error_please_enter_first_name)
            )

            lastName.isBlank() -> setError(
                etLastName, context.getString(R.string.error_please_enter_last_name)
            )

            birthday.isBlank() -> setError(
                etBirthday, context.getString(R.string.error_please_enter_birthday)
            )

            address.isBlank() -> setError(
                etAddress, context.getString(R.string.error_please_enter_address)
            )

            email.isBlank() -> setError(
                etEmail, context.getString(R.string.error_please_enter_email)
            )

            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> setError(
                etEmail, context.getString(R.string.error_invalid_email_format)
            )

            else -> true
        }
    }
}