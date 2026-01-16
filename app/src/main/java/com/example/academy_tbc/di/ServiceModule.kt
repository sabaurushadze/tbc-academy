package com.example.academy_tbc.di

import com.example.academy_tbc.data.remote.service.category.CategoryService
import com.example.academy_tbc.data.remote.service.outfit.OutfitService
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
    fun provideCategoryService(retrofit: Retrofit): CategoryService {
        return retrofit.create(CategoryService::class.java)
    }

    @Provides
    @Singleton
    fun provideOutfitService(retrofit: Retrofit): OutfitService {
        return retrofit.create(OutfitService::class.java)
    }
}