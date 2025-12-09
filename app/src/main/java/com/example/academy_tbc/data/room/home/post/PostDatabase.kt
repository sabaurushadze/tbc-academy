package com.example.academy_tbc.data.room.home.post

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.academy_tbc.data.room.converters.Converters

@Database(entities = [PostEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class PostDatabase : RoomDatabase() {
    abstract fun postDao(): PostDao
}