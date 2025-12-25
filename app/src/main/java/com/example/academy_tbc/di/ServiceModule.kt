package com.example.academy_tbc.di

import com.example.academy_tbc.data.service.auth.departments.DepartmentService
import com.example.academy_tbc.data.service.auth.sign_in.SignInService
import com.example.academy_tbc.data.service.auth.sign_up.SignUpService
import com.example.academy_tbc.data.service.categories.CategoryService
import com.example.academy_tbc.data.service.event_registration.EventRegistrationService
import com.example.academy_tbc.data.service.events.EventService
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
    fun provideSignInService(retrofit: Retrofit): SignInService {
        return retrofit.create(SignInService::class.java)
    }

    @Provides
    @Singleton
    fun provideSignUpService(retrofit: Retrofit): SignUpService {
        return retrofit.create(SignUpService::class.java)
    }

    @Provides
    @Singleton
    fun provideEventService(retrofit: Retrofit): EventService {
        return retrofit.create(EventService::class.java)
    }

    @Provides
    @Singleton
    fun provideCategoryService(retrofit: Retrofit): CategoryService {
        return retrofit.create(CategoryService::class.java)
    }

    @Provides
    @Singleton
    fun provideDepartmentService(retrofit: Retrofit): DepartmentService {
        return retrofit.create(DepartmentService::class.java)
    }

    @Provides
    @Singleton
    fun provideEventRegistrationService(retrofit: Retrofit): EventRegistrationService {
        return retrofit.create(EventRegistrationService::class.java)
    }
}