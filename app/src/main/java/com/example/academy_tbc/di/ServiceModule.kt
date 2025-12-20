package com.example.academy_tbc.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

//@Module
//@InstallIn(SingletonComponent::class)
//object ServiceModule {
//    @Provides
//    @Singleton
//    fun provideLogInService(retrofit: Retrofit): LogInApiService {
//        return retrofit.create(LogInApiService::class.java)
//    }
//}