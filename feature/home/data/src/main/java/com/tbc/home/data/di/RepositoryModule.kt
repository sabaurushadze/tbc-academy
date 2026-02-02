package com.tbc.home.data.di

import com.tbc.home.data.repository.form.FormRepositoryImpl
import com.tbc.home.domain.repository.form.FormRepository
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
    abstract fun bindStoryRepository(impl: FormRepositoryImpl): FormRepository
}