package com.example.academy_tbc.domain.repository.datastore

import com.example.academy_tbc.data.local.datastore.keys.PreferenceKey
import kotlinx.coroutines.flow.Flow

interface CacheManager {

    fun <T> observe(key: PreferenceKey<T>): Flow<T?>

    suspend fun <T> save(key: PreferenceKey<T>, value: T)

    suspend fun clear()
}