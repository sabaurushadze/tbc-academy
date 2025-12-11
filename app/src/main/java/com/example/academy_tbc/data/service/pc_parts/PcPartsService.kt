package com.example.academy_tbc.data.service.pc_parts

import com.example.academy_tbc.data.model.response.pc_parts.PcPartsResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PcPartsService {
//    @GET(ENDPOINT_PC_PARTS)
//    suspend fun getParts(): Response<List<PcPartsResponseDto>>

    @GET(ENDPOINT_PC_PARTS)
    suspend fun search(
        @Query("title_like") query: String? = null,
        @Query("category") category: Int? = null,
        @Query("price_gte") minPrice: Float? = null,
        @Query("price_lte") maxPrice: Float? = null,
        @Query("condition") condition: String? = null,
        @Query("_sort") sortBy: String? = null,
        @Query("_order") sortOrder: String? = null,
        @Query("_page") page: Int,
        @Query("_per_page") perPage: Int
    ): Response<List<PcPartsResponseDto>>

//    @GET(ENDPOINT_PC_PARTS)
//    suspend fun getPartsByCategory(
//        @Query(QUERY_CATEGORY) query: String
//    ): Response<List<PcPartsResponseDto>>

    companion object {
        private const val ENDPOINT_PC_PARTS = "pc_parts"
        private const val QUERY_TITLE_LIKE = "title_like"
        private const val QUERY_CATEGORY = "category"

    }
}