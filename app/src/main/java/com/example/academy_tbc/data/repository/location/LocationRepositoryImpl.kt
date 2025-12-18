package com.example.academy_tbc.data.repository.location

import com.example.academy_tbc.data.local.room.location.LocationLocalDataSource
import com.example.academy_tbc.data.local.room.mapper.toDomain
import com.example.academy_tbc.data.local.room.mapper.toEntity
import com.example.academy_tbc.data.remote.datasource.LocationRemoteDataSource
import com.example.academy_tbc.domain.common.ApiError
import com.example.academy_tbc.domain.common.Resource
import com.example.academy_tbc.domain.model.location.Location
import com.example.academy_tbc.domain.repository.location.LocationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(
    private val localDataSource: LocationLocalDataSource,
    private val remoteDataSource: LocationRemoteDataSource,
) : LocationRepository {
    override fun getLocations(): Flow<Resource<List<Location>, ApiError>> = flow {
        emit(Resource.Loading)

        try {
            val networkDtos = remoteDataSource.fetchLocations()
            val networkEntities = networkDtos.map { it.toEntity() }
            withContext(Dispatchers.IO) {
                localDataSource.upsertLocations(networkEntities)
            }
        } catch (_: IOException) {
            emit(Resource.Error(ApiError.NETWORK_ERROR))
        }

        emitAll(
            localDataSource.observeLocations()
                .map { entities -> Resource.Success(entities.map { it.toDomain() }) }
        )
    }
}
