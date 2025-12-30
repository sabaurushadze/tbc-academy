package com.example.academy_tbc.di

import com.example.academy_tbc.data.remote.service.login.EquipmentApiService
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
    fun provideEquipmentService(retrofit: Retrofit): EquipmentApiService {
        return retrofit.create(EquipmentApiService::class.java)
    }
}