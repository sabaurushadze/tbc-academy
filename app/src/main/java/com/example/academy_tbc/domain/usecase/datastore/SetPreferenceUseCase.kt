package com.example.academy_tbc.domain.usecase.datastore

import androidx.datastore.preferences.core.Preferences
import com.example.academy_tbc.domain.repository.datastore.DataStoreManager
import jakarta.inject.Inject

class SetPreferenceUseCase @Inject constructor(
    private val preferencesRepository: DataStoreManager
) {
    suspend fun <T> invoke(key: Preferences.Key<T>, value: T) {
        preferencesRepository.setPreference(key, value)
    }
}