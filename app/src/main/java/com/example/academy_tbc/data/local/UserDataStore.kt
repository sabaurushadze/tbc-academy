package com.example.academy_tbc.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserDataStore(private val dataStore: DataStore<Preferences>) {
    private object PreferenceKeys {
        val USER_TOKEN = stringPreferencesKey(KEY_USER_TOKEN)
        val USER_EMAIL = stringPreferencesKey(KEY_USER_EMAIL)
    }

    val getToken: Flow<String> = dataStore.data.map { preferences ->
        preferences[PreferenceKeys.USER_TOKEN] ?: ""
    }

    val getEmail: Flow<String> = dataStore.data.map { preferences ->
        preferences[PreferenceKeys.USER_EMAIL] ?: ""
    }

    suspend fun removeToken() {
        dataStore.edit { preferences ->
            preferences.remove(PreferenceKeys.USER_TOKEN)
        }
    }

    suspend fun removeEmail() {
        dataStore.edit { preferences ->
            preferences.remove(PreferenceKeys.USER_EMAIL)
        }
    }

    suspend fun saveToken(token: String) {
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.USER_TOKEN] = token
        }
    }

    suspend fun saveEmail(email: String) {
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.USER_EMAIL] = email
        }
    }

    companion object {
        const val KEY_USER_TOKEN = "user_token"
        const val KEY_USER_EMAIL = "user_email"
    }

}