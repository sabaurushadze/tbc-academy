package com.example.academy_tbc

import android.app.Application
import com.example.academy_tbc.data.AppContainer
import com.example.academy_tbc.data.DefaultAppContainer

class AuthApplication : Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}