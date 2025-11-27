package com.example.academy_tbc.data.local.preferences

import androidx.datastore.preferences.core.stringPreferencesKey

object PreferenceKeys {
    val USER_TOKEN = stringPreferencesKey("user_token")
    val USER_EMAIL = stringPreferencesKey("user_email")
}