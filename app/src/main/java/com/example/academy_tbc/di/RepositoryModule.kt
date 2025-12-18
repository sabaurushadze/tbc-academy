package com.example.academy_tbc.di

import com.example.academy_tbc.data.local.datastore.DataStoreManager
import com.example.academy_tbc.data.repository.location.LocationRepositoryImpl
import com.example.academy_tbc.domain.repository.datastore.CacheManager
import com.example.academy_tbc.domain.repository.location.LocationRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Suppress("unused")
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindDatastore(dataStoreManager: DataStoreManager): CacheManager

    @Binds
    @Singleton
    abstract fun bindLogInRepository(
        impl: LocationRepositoryImpl,
    ): LocationRepository
}