package com.example.academy_tbc.data.remote.service.outfit

import com.example.academy_tbc.data.remote.dto.response.outfit.OutfitResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface OutfitService {
    @GET(OUTFIT)
    suspend fun getOutfits(): Response<List<OutfitResponseDto>>

    @GET(OUTFIT)
    suspend fun getOutfitsByCategory(
        @Query("category") categoryId: Int
    ): Response<List<OutfitResponseDto>>
    companion object {
        private const val OUTFIT = "outfits"
    }
}