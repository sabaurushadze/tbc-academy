package com.example.academy_tbc.data.local.room.location

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocationLocalDataSource @Inject constructor(
    private val dao: LocationDao
) {
    fun observeLocations(): Flow<List<LocationEntity>> = dao.getAll()

    fun upsertLocations(locations: List<LocationEntity>) {
        dao.upsertAllLocations(locations)
    }
}