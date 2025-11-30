package com.example.academy_tbc.data.preferences

import com.example.academy_tbc.domain.common.PreferenceKey

object PreferenceKeys {
    val USER_TOKEN = PreferenceKey(USER_TOKEN_VALUE, "")
    val USER_EMAIL = PreferenceKey(USER_EMAIL_VALUE, "")

    private const val USER_TOKEN_VALUE = "user_token"
    private const val USER_EMAIL_VALUE = "user_email"
}
