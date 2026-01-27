package com.example.academy_tbc.data.repository.location

import com.example.academy_tbc.data.common.ApiResponseHandler
import com.example.academy_tbc.data.remote.mapper.toDomain
import com.example.academy_tbc.data.remote.service.location.LocationService
import com.example.academy_tbc.domain.common.DataError
import com.example.academy_tbc.domain.common.Resource
import com.example.academy_tbc.domain.common.mapList
import com.example.academy_tbc.domain.model.location.Location
import com.example.academy_tbc.domain.repository.location.LocationRepository
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(
    private val locationService: LocationService,
    private val responseHandler: ApiResponseHandler,
) : LocationRepository {
    override suspend fun getLocations(): Resource<List<Location>, DataError.Network> {
        return responseHandler.safeApiCall {
            locationService.getLocations()
        }.mapList { it.toDomain() }
    }
}