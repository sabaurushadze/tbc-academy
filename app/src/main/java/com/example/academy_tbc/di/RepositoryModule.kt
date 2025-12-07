package com.example.academy_tbc.di

import com.example.academy_tbc.data.repository.datastore.DataStoreRepositoryImpl
import com.example.academy_tbc.data.repository.pc_parts.GetPcPartsRepositoryImpl
import com.example.academy_tbc.data.repository.pc_parts.SearchPcPartsRepositoryImpl
import com.example.academy_tbc.domain.repository.datastore.DataStoreRepository
import com.example.academy_tbc.domain.repository.pc_parts.GetPcPartsRepository
import com.example.academy_tbc.domain.repository.pc_parts.SearchPcPartsRepository
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
    abstract fun bindGetPcPartsRepository(
        impl: GetPcPartsRepositoryImpl,
    ): GetPcPartsRepository


    @Binds
    @Singleton
    abstract fun bindSearchPcPartsRepository(
        impl: SearchPcPartsRepositoryImpl,
    ): SearchPcPartsRepository
}