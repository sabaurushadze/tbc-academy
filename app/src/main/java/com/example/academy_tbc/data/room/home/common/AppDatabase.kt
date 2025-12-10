package com.example.academy_tbc.data.room.home.common

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.academy_tbc.data.room.converters.Converters
import com.example.academy_tbc.data.room.home.location.LocationDao
import com.example.academy_tbc.data.room.home.location.LocationEntity
import com.example.academy_tbc.data.room.home.post.PostDao
import com.example.academy_tbc.data.room.home.post.PostEntity

@Database(entities = [PostEntity::class, LocationEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun postDao(): PostDao
    abstract fun locationDao(): LocationDao
}