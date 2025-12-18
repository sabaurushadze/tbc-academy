package com.example.academy_tbc.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.academy_tbc.data.local.room.location.LocationDao
import com.example.academy_tbc.data.local.room.location.LocationEntity

@Database(entities = [LocationEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun locationDao(): LocationDao
}