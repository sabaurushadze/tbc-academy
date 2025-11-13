package com.example.academy_tbc.data.auth

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserTokenRepository(private val dataStore: DataStore<Preferences>) {
    private object PreferenceKeys {
        val USER_TOKEN = stringPreferencesKey(KEY_USER_TOKEN)
    }

    val getToken: Flow<String> = dataStore.data.map { preferences ->
        preferences[PreferenceKeys.USER_TOKEN] ?: ""
    }

    suspend fun removeToken() {
        dataStore.edit { preferences ->
            preferences.remove(PreferenceKeys.USER_TOKEN)
        }
    }

    suspend fun saveToken(token: String) {
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.USER_TOKEN] = token
        }
    }

    companion object {
        const val KEY_USER_TOKEN = "user_token"
    }

}