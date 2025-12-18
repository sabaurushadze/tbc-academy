package com.example.academy_tbc.data.remote.datasource

import com.example.academy_tbc.data.remote.model.response.location.LocationResponseDto
import com.example.academy_tbc.data.remote.service.location.LocationApiService
import javax.inject.Inject

class LocationRemoteDataSource @Inject constructor(
    private val api: LocationApiService,
) {
    suspend fun fetchLocations(): List<LocationResponseDto> {
        return api.getLocations().body().orEmpty()
    }
}