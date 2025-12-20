package com.example.academy_tbc.domain.usecase.datastore

import androidx.datastore.preferences.core.Preferences
import com.example.academy_tbc.domain.repository.datastore.DataStoreManager
import jakarta.inject.Inject

class RemovePreferencesUseCase @Inject constructor(
    private val preferencesRepository: DataStoreManager
)  {
    suspend operator fun invoke(keys: List<Preferences.Key<*>>) {
        preferencesRepository.removePreferences(keys)
    }
}