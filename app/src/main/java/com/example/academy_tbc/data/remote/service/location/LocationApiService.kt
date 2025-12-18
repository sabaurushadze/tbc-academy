package com.example.academy_tbc.data.remote.service.location

import com.example.academy_tbc.data.remote.model.response.location.LocationResponseDto
import retrofit2.Response
import retrofit2.http.GET

interface LocationApiService {
    @GET(LOCATIONS)
    suspend fun getLocations(): Response<List<LocationResponseDto>>

    companion object {
        private const val LOCATIONS = "d7c6d734-6080-4045-a196-7da16339b6d7"

    }
}