package com.example.academy_tbc.di

import com.example.academy_tbc.data.repository.auth.FirebaseAuthRepositoryImpl
import com.example.academy_tbc.data.repository.category.CategoryRepositoryImpl
import com.example.academy_tbc.data.repository.part_details.PartDetailsRepositoryImpl
import com.example.academy_tbc.data.repository.pc_parts.PcPartsRepositoryImpl
import com.example.academy_tbc.domain.repository.auth.AuthRepository
import com.example.academy_tbc.domain.repository.category.CategoryRepository
import com.example.academy_tbc.domain.repository.part_details.PartDetailsRepository
import com.example.academy_tbc.domain.repository.pc_parts.PcPartsRepository
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
    abstract fun bindGetPcPartsRepository(
        impl: PcPartsRepositoryImpl,
    ): PcPartsRepository


    @Binds
    @Singleton
    abstract fun bindPartDetailsRepository(
        impl: PartDetailsRepositoryImpl,
    ): PartDetailsRepository

    @Binds
    @Singleton
    abstract fun bindCategoryRepository(
        impl: CategoryRepositoryImpl,
    ): CategoryRepository

    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        impl: FirebaseAuthRepositoryImpl,
    ): AuthRepository


}