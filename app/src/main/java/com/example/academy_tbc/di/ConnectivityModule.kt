package com.example.academy_tbc.di

import com.example.academy_tbc.domain.manager.NetworkStateManager
import com.example.academy_tbc.presentation.manager.network.NetworkStateManagerImpl
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
        impl: NetworkStateManagerImpl,
    ): NetworkStateManager

}