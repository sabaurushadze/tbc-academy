package com.example.academy_tbc.domain.usecase.datastore

import androidx.datastore.preferences.core.Preferences
import com.example.academy_tbc.domain.repository.datastore.DataStoreManager
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetPreferenceUseCase @Inject constructor(private val preferencesRepository: DataStoreManager)  {
    suspend fun <T> invoke(key: Preferences.Key<T>, defaultValue: T): Flow<T> {
        return preferencesRepository.getPreference(key, defaultValue)
    }
}