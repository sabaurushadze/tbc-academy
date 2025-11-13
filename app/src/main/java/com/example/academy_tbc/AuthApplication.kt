package com.example.academy_tbc

import android.app.Application
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.example.academy_tbc.data.auth.AppContainer
import com.example.academy_tbc.data.auth.DefaultAppContainer

class AuthApplication : Application() {
    lateinit var container: AppContainer
        private set

    val dataStore: DataStore<Preferences> by lazy {
        PreferenceDataStoreFactory.create {
            preferencesDataStoreFile("user_prefs")
        }
    }

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer(dataStore)
    }
}