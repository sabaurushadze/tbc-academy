package com.example.academy_tbc.utils

import android.content.Context
import android.widget.EditText
import com.example.academy_tbc.R

object Validations {
    fun validateEmailAndPassword(
        context: Context,
        email: String,
        password: String,
        emailEditText: EditText,
        passwordEditText: EditText
    ): Boolean {
        return if (email.isEmpty()) {
            emailEditText.error = context.getString(R.string.error_please_enter_an_email_address)
            false
        } else if (password.isEmpty()) {
            passwordEditText.error = context.getString(R.string.error_please_enter_a_password)
            false
        } else if (password.length < 8) {
            passwordEditText.error = context.getString(R.string.password_too_short)
            false
        } else if (password.length > 25) {
            passwordEditText.error = context.getString(R.string.password_too_long)
            false
        } else {
            true
        }
    }

    fun validateUsername(
        context: Context,
        username: String,
        usernameEditText: EditText
    ): Boolean {
        return if (username.isEmpty()) {
            usernameEditText.error = context.getString(R.string.please_enter_a_username)
            false
        } else if (username.length < 4) {
            usernameEditText.error = context.getString(R.string.username_too_short)
            false
        } else if (username.length > 25) {
            usernameEditText.error = context.getString(R.string.username_too_long)
            false
        } else {
            true
        }
    }
}