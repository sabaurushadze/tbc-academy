package com.example.academy_tbc.di

import android.content.Context
import androidx.room.Room
import com.example.academy_tbc.data.room.home.common.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "location_database"
        ).build()
    }

    @Provides
    fun provideLocationDao(db: AppDatabase) = db.locationDao()

    @Provides
    fun providePostDao(db: AppDatabase) = db.postDao()
}