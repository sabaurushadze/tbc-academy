package com.example.academy_tbc.domain.repository.datastore

import com.example.academy_tbc.domain.common.PreferenceKey
import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {
    fun <T> get(key: PreferenceKey<T>, defaultValue: T): Flow<T>
    suspend fun <T> getOnce(key: PreferenceKey<T>, defaultValue: T): T
    suspend fun <T> put(key: PreferenceKey<T>, value: T)
    suspend fun <T> remove(key: PreferenceKey<T>)
    suspend fun clear()
}