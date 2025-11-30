package com.example.academy_tbc.data.repository.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.example.academy_tbc.data.mapper.datastore.toDataStoreKey
import com.example.academy_tbc.domain.common.PreferenceKey
import com.example.academy_tbc.domain.repository.datastore.DataStoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStoreRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>,
) : DataStoreRepository {

    override suspend fun <T> putPreference(
        key: PreferenceKey<T>,
        value: T,
    ) {
        dataStore.edit { preferences ->
            preferences[key.toDataStoreKey()] = value
        }
    }

    override suspend fun <T> getFirstPreference(
        key: PreferenceKey<T>,
        defaultValue: T,
    ): T {
        return dataStore.data.first()[key.toDataStoreKey()] ?: defaultValue
    }

    override fun <T> getPreference(
        key: PreferenceKey<T>,
        defaultValue: T,
    ): Flow<T> {
        return dataStore.data.map { preferences ->
            preferences[key.toDataStoreKey()] ?: defaultValue
        }
    }

    override suspend fun <T> removePreference(key: PreferenceKey<T>) {
        dataStore.edit { preferences ->
            preferences.remove(key.toDataStoreKey())
        }
    }

    override suspend fun clearAll() {
        dataStore.edit { it.clear() }
    }
}