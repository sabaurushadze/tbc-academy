package com.example.academy_tbc.utils

import com.example.academy_tbc.R
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException

enum class AuthError(val messageResId: Int) {
    USER_COLLISION(R.string.error_email_is_already_in_use),
    INVALID_CREDENTIALS(R.string.error_invalid_login_credentials),
    WEAK_PASSWORD(R.string.error_password_too_weak),
    NETWORK_FAILURE(R.string.error_no_internet_connection),
    UNKNOWN_ERROR(R.string.error_registration_failed_please_try_again);

    companion object {
        fun fromException(exception: Exception?): AuthError {
            return when (exception) {
                is FirebaseAuthUserCollisionException -> USER_COLLISION
                is FirebaseAuthWeakPasswordException -> WEAK_PASSWORD
                is FirebaseAuthInvalidCredentialsException -> INVALID_CREDENTIALS
                is FirebaseNetworkException -> NETWORK_FAILURE
                else -> UNKNOWN_ERROR
            }
        }
    }
}