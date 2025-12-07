package com.example.academy_tbc.data.service.pc_parts

import com.example.academy_tbc.data.model.response.pc_parts.PcPartsResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PcPartsService {
    @GET("pc_parts")
    suspend fun getParts(): Response<List<PcPartsResponseDto>>

    @GET("pc_parts")
    suspend fun search(
        @Query("title_like") query: String
    ): Response<List<PcPartsResponseDto>>
}