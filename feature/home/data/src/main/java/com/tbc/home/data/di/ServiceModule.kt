package com.tbc.home.data.di

import com.tbc.home.data.remote.service.form.FormService
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
    fun provideFormService(retrofit: Retrofit): FormService {
        return retrofit.create(FormService::class.java)
    }
}