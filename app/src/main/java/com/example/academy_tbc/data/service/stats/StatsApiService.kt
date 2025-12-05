package com.example.academy_tbc.data.service.stats

import com.example.academy_tbc.data.model.response.stats.StatsResponseDto
import retrofit2.Response
import retrofit2.http.GET

interface StatsApiService {
    @GET(STATISTICS)
    suspend fun getStatistics() : Response<List<StatsResponseDto>>

    companion object {
        private const val STATISTICS = "e3215354-6784-4bae-9bb9-25b39360971b"
    }
}