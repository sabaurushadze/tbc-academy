package com.example.academy_tbc.di

import android.content.Context
import androidx.room.Room
import com.example.academy_tbc.data.room.home.location.LocationDatabase
import com.example.academy_tbc.data.room.home.post.PostDatabase
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
    fun provideLocationDatabase(@ApplicationContext context: Context): LocationDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            LocationDatabase::class.java,
            "location_database"
        ).build()
    }

    @Provides
    fun provideLocationDao(db: LocationDatabase) = db.locationDao()


    @Provides
    @Singleton
    fun providePostDatabase(@ApplicationContext context: Context): PostDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            PostDatabase::class.java,
            "post_database"
        ).build()
    }

    @Provides
    fun providePostDao(db: PostDatabase) = db.postDao()
}