package com.example.academy_tbc.di

import com.example.academy_tbc.data.repository.datastore.DataStoreManagerImpl
import com.example.academy_tbc.data.repository.form.FormRepositoryImpl
import com.example.academy_tbc.domain.repository.datastore.DataStoreManager
import com.example.academy_tbc.domain.repository.form.FormRepository
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
    abstract fun bindStoryRepository(impl: FormRepositoryImpl): FormRepository
}