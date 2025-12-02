package com.example.academy_tbc.data.mapper.datastore

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.academy_tbc.domain.common.PreferenceKey

fun <T> PreferenceKey<T>.toDataStoreKey(): Preferences.Key<T> {
    @Suppress("UNCHECKED_CAST")
    return when (defaultValue) {
        is String -> stringPreferencesKey(name)
        is Boolean -> booleanPreferencesKey(name)
        is Int -> intPreferencesKey(name)
        is Long -> longPreferencesKey(name)
        is Float -> floatPreferencesKey(name)
        else -> throw IllegalArgumentException("Unsupported type for DataStore")
    } as Preferences.Key<T>
}