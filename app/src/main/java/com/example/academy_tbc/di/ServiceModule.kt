package com.example.academy_tbc.di

import com.example.academy_tbc.data.service.home.LocationApiService
import com.example.academy_tbc.data.service.home.PostApiService
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
    fun provideLocationService(retrofit: Retrofit): LocationApiService {
        return retrofit.create(LocationApiService::class.java)
    }

    @Provides
    @Singleton
    fun providePostService(retrofit: Retrofit): PostApiService {
        return retrofit.create(PostApiService::class.java)
    }
}