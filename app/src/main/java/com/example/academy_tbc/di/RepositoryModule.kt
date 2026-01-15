package com.example.academy_tbc.di

import com.example.academy_tbc.data.repository.auth.login.LogInRepositoryImpl
import com.example.academy_tbc.data.repository.auth.register.RegisterRepositoryImpl
import com.example.academy_tbc.data.repository.datastore.DataStoreManagerImpl
import com.example.academy_tbc.domain.repository.auth.login.LogInRepository
import com.example.academy_tbc.domain.repository.auth.register.RegisterRepository
import com.example.academy_tbc.domain.repository.datastore.DataStoreManager
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
    abstract fun bindDatastore(dataStoreManager: DataStoreManagerImpl): DataStoreManager

    @Binds
    @Singleton
    abstract fun bindLogInRepository(impl: LogInRepositoryImpl): LogInRepository

    @Binds
    @Singleton
    abstract fun bindRegisterRepository(impl: RegisterRepositoryImpl): RegisterRepository
}