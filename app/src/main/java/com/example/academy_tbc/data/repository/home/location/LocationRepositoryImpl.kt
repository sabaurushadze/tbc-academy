package com.example.academy_tbc.data.repository.home.location

import com.example.academy_tbc.data.common.ResponseHandler
import com.example.academy_tbc.data.mapper.home.location.toDomain
import com.example.academy_tbc.data.room.home.location.LocationDao
import com.example.academy_tbc.data.room.home.location.LocationEntity
import com.example.academy_tbc.data.room.home.location.mapper.toDomain
import com.example.academy_tbc.data.service.home.LocationApiService
import com.example.academy_tbc.domain.model.home.location.Location
import com.example.academy_tbc.domain.repository.home.location.LocationRepository
import com.example.academy_tbc.domain.resource.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(
    private val authService: LocationApiService,
    private val locationDao: LocationDao,
    private val responseHandler: ResponseHandler,
) : LocationRepository {
    override fun getCachedLocations(): Flow<List<Location>> {
        return locationDao.getAll().map { list -> list.map { it.toDomain() } }
    }

    override fun getLocations(): Flow<Resource<List<Location>>> = flow {

        responseHandler.safeApiCall { authService.getLocations() }.collect { resource ->
            when (resource) {
                is Resource.Success -> {
                    val locations = resource.data

                    val entities = locations.map { dto ->
                        LocationEntity(
                            title = dto.title,
                            cover = dto.cover,
                            id = 0
                        )
                    }
                    locationDao.insertUsers(*entities.toTypedArray())

                    emit(Resource.Success(locations.map { it.toDomain() }))
                }

                is Resource.Error -> emit(resource)
                is Resource.Loading -> emit(resource)
            }
        }
    }
}