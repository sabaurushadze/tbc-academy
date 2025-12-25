package com.example.academy_tbc.domain.preferences

import androidx.datastore.preferences.core.stringPreferencesKey

object AppPreferencesKeys {
    val TOKEN = stringPreferencesKey("access_token")
    val EMAIL = stringPreferencesKey("email")
    val FULL_NAME = stringPreferencesKey("full_name")
    val PHONE_NUMBER = stringPreferencesKey("phone_number")
    val DEPARTMENT = stringPreferencesKey("department")
}