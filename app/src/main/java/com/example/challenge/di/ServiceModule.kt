package com.example.challenge.di

import com.example.challenge.data.service.connection.ConnectionsService
import com.example.challenge.data.service.log_in.LogInService
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
    fun provideLogInService(retrofit: Retrofit): LogInService {
        return retrofit.create(LogInService::class.java)
    }

    @Provides
    @Singleton
    fun provideConnectionsService(retrofit: Retrofit): ConnectionsService {
        return retrofit.create(ConnectionsService::class.java)
    }
}