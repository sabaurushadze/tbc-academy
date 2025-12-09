package com.example.academy_tbc.data.service.home

import com.example.academy_tbc.data.model.response.home.LocationResponseDto
import retrofit2.Response
import retrofit2.http.GET

interface LocationApiService {
    @GET("0f76d541-3832-4a3c-927a-0593e060d6da")
    suspend fun getLocations(): Response<List<LocationResponseDto>>
}