package com.example.academy_tbc.data.remote.service.location

import com.example.academy_tbc.data.remote.dto.response.locations.LocationResponseDto
import retrofit2.Response
import retrofit2.http.GET

interface LocationService {

    @GET(LOCATIONS)
    suspend fun getLocations(): Response<List<LocationResponseDto>>

    companion object {
        private const val LOCATIONS = "locations"
    }
}