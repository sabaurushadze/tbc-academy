package com.example.academy_tbc.data.room.home.location

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface LocationDao {
    @Upsert
    suspend fun insertUsers(vararg locations: LocationEntity)

    @Query("SELECT * FROM locationentity")
    fun getAll(): Flow<List<LocationEntity>>
}