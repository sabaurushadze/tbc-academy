package com.example.academy_tbc.di

import android.content.Context
import androidx.room.Room
import com.example.academy_tbc.data.local.room.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun provideRoomDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "location_database"
        ).build()
    }

    @Provides
    fun provideUserDao(db: AppDatabase) = db.locationDao()
}