package com.example.academy_tbc.di

import com.example.academy_tbc.data.repository.datastore.DataStoreRepositoryImpl
import com.example.academy_tbc.data.repository.stats.StatsRepositoryImpl
import com.example.academy_tbc.domain.repository.datastore.DataStoreRepository
import com.example.academy_tbc.domain.repository.stats.StatsRepository
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
    abstract fun bindDataStoreRepository(
        impl: DataStoreRepositoryImpl,
    ): DataStoreRepository

    @Binds
    @Singleton
    abstract fun bindLogInRepository(
        impl: StatsRepositoryImpl,
    ): StatsRepository
}