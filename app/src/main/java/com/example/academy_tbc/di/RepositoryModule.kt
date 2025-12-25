package com.example.academy_tbc.di

import com.example.academy_tbc.data.repository.auth.departments.DepartmentRepositoryImpl
import com.example.academy_tbc.data.repository.auth.sign_in.SignInRepositoryImpl
import com.example.academy_tbc.data.repository.auth.sign_up.SignUpRepositoryImpl
import com.example.academy_tbc.data.repository.categories.CategoryRepositoryImpl
import com.example.academy_tbc.data.repository.datastore.DataStoreManagerImpl
import com.example.academy_tbc.data.repository.event_registration.EventRegistrationRepositoryImpl
import com.example.academy_tbc.data.repository.events.EventRepositoryImpl
import com.example.academy_tbc.domain.repository.auth.departments.DepartmentRepository
import com.example.academy_tbc.domain.repository.auth.sign_in.SignInRepository
import com.example.academy_tbc.domain.repository.auth.sign_up.SignUpRepository
import com.example.academy_tbc.domain.repository.categories.CategoryRepository
import com.example.academy_tbc.domain.repository.datastore.DataStoreManager
import com.example.academy_tbc.domain.repository.event_registration.EventRegistrationRepository
import com.example.academy_tbc.domain.repository.events.EventRepository
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
    abstract fun bindDatastore(dataStoreManager: DataStoreManagerImpl): DataStoreManager

    @Binds
    @Singleton
    abstract fun bindSignInRepository(
        impl: SignInRepositoryImpl,
    ): SignInRepository

    @Binds
    @Singleton
    abstract fun bindSignUpRepository(
        impl: SignUpRepositoryImpl,
    ): SignUpRepository

    @Binds
    @Singleton
    abstract fun bindEventRepository(
        impl: EventRepositoryImpl,
    ): EventRepository

    @Binds
    @Singleton
    abstract fun bindCategoryRepository(
        impl: CategoryRepositoryImpl,
    ): CategoryRepository

    @Binds
    @Singleton
    abstract fun bindDepartmentRepository(
        impl: DepartmentRepositoryImpl,
    ): DepartmentRepository

    @Binds
    @Singleton
    abstract fun bindEventRegistrationRepository(
        impl: EventRegistrationRepositoryImpl,
    ): EventRegistrationRepository
}