package com.example.academy_tbc.domain.repository.datastore

import com.example.academy_tbc.domain.common.PreferenceKey
import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {
    fun <T> getPreference(key: PreferenceKey<T>, defaultValue: T): Flow<T>
    suspend fun <T> getFirstPreference(key: PreferenceKey<T>, defaultValue: T): T
    suspend fun <T> putPreference(key: PreferenceKey<T>, value: T)
    suspend fun <T> removePreference(key: PreferenceKey<T>)
    suspend fun clearAll()
}