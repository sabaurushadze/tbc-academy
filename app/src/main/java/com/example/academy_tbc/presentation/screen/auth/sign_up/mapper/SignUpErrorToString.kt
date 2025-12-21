package com.example.academy_tbc.presentation.screen.auth.sign_up.mapper

import com.example.academy_tbc.R
import com.example.academy_tbc.domain.model.auth.sign_in.SignInValidationError
import com.example.academy_tbc.domain.model.auth.sign_up.SignUpValidationError
import com.example.academy_tbc.presentation.util.GenericString

fun SignUpValidationError.toGenericString(): GenericString {
    val stringRes = when (this) {
        SignUpValidationError.EMAIL_EMPTY -> R.string.email_is_empty
        SignUpValidationError.PASSWORD_EMPTY -> R.string.password_is_empty
        SignUpValidationError.EMAIL_WRONG_FORMAT -> R.string.wrong_email_format
        SignUpValidationError.VALID -> R.string.valid
        SignUpValidationError.FIRST_NAME_EMPTY -> R.string.first_name_empty
        SignUpValidationError.LAST_NAME_EMPTY -> R.string.last_name_empty
        SignUpValidationError.PASSWORD_WRONG_FORMAT -> R.string.password_must_be_at_least_8_characters_with_uppercase_lowercase_and_number
        SignUpValidationError.WRONG_PHONE_NUMBER_FORMAT -> R.string.wrong_phone_number_format
        SignUpValidationError.WRONG_OTP_FORMAT -> R.string.wrong_otp_format
        SignUpValidationError.DEPARTMENT_NOT_SELECTED -> R.string.department_not_selected
        SignUpValidationError.CONFIRM_PASSWORD_EMPTY -> R.string.confirm_password_empty
        SignUpValidationError.TOS_NOT_SELECTED -> R.string.you_need_to_agree_to_tos
        SignUpValidationError.CONFIRM_PASSWORD_MISMATCH -> R.string.password_does_not_match_confirm_password
    }
    return GenericString.StringResource(stringRes)
}