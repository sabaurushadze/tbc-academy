package com.example.academy_tbc.di

import com.example.academy_tbc.domain.validator.EmailValidator
import com.example.academy_tbc.presentation.validator.AndroidEmailValidator
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Suppress("unused")
@Module
@InstallIn(SingletonComponent::class)
abstract class ValidationModule {

    @Binds
    abstract fun bindEmailValidator(
        impl: AndroidEmailValidator
    ): EmailValidator
}