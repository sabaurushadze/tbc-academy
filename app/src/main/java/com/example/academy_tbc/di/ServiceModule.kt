package com.example.academy_tbc.di

import com.example.academy_tbc.data.service.login.LogInApiService
import com.example.academy_tbc.data.service.register.RegisterApiService
import com.example.academy_tbc.data.service.users.UsersApiService
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
    fun provideLogInService(retrofit: Retrofit): LogInApiService {
        return retrofit.create(LogInApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideRegisterService(retrofit: Retrofit): RegisterApiService {
        return retrofit.create(RegisterApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideUsersService(retrofit: Retrofit): UsersApiService {
        return retrofit.create(UsersApiService::class.java)
    }
}