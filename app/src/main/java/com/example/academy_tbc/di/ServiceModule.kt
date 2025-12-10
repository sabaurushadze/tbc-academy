package com.example.academy_tbc.di

import com.example.academy_tbc.data.service.categories.CategoriesService
import com.example.academy_tbc.data.service.pc_parts.PcPartsService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {
    @Provides
    @Singleton
    fun providePcPartsService(retrofit: Retrofit): PcPartsService {
        return retrofit.create(PcPartsService::class.java)
    }

    @Provides
    @Singleton
    fun provideCategoriesService(retrofit: Retrofit): CategoriesService {
        return retrofit.create(CategoriesService::class.java)
    }
}