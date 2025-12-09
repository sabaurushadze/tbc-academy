package com.example.academy_tbc.di

import com.example.academy_tbc.data.manager.ConnectivityObserverImpl
import com.example.academy_tbc.domain.observer.ConnectivityObserver
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Suppress("unused")
@Module
@InstallIn(SingletonComponent::class)
abstract class ConnectivityModule {

    @Binds
    @Singleton
    abstract fun bindConnectivityManager(
        impl: ConnectivityObserverImpl,
    ): ConnectivityObserver

}