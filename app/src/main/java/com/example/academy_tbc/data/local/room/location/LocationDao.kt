package com.example.academy_tbc.data.local.room.location

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface LocationDao {
    @Query("SELECT * FROM locationentity")
    fun getAll(): Flow<List<LocationEntity>>

    @Upsert
    fun upsertAllLocations(locations: List<LocationEntity>)
}