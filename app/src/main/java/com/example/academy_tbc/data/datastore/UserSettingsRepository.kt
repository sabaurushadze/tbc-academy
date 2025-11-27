package com.example.academy_tbc.data.datastore

import androidx.datastore.core.DataStore
import com.example.academy_tbc.UserSettings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import java.io.IOException
import javax.inject.Inject

class UserSettingsRepository @Inject constructor(
    private val dataStore: DataStore<UserSettings>,
) {
    val userSettings: Flow<UserSettings> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(UserSettings.getDefaultInstance())
            } else throw exception
        }

    suspend fun saveUserSettings(
        firstName: String?,
        lastName: String?,
        email: String?,
    ) {
        dataStore.updateData { current ->
            current.toBuilder()
                .setFirstName(firstName ?: current.firstName)
                .setLastName(lastName ?: current.lastName)
                .setEmail(email ?: current.email)
                .build()
        }
    }
}