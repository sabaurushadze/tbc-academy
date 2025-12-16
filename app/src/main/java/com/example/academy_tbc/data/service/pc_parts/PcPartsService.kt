package com.example.academy_tbc.data.service.pc_parts

import com.example.academy_tbc.data.model.response.part_details.PartDetailResponseDto
import com.example.academy_tbc.data.model.response.pc_parts.PcPartsResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PcPartsService {
    @GET(ENDPOINT_PC_PARTS)
    suspend fun search(
        @Query(TITLE_LIKE) query: String? = null,
        @Query(CATEGORY) category: Int? = null,
        @Query(PRICE_GREATER_THAN) minPrice: Float? = null,
        @Query(PRICE_LESS_THAN) maxPrice: Float? = null,
        @Query(CONDITION) condition: List<String>? = null,
        @Query(BRAND) brand: List<String>? = null,
        @Query(MODEL) model: List<String>? = null,
        @Query(SORT) sortBy: String? = null,
        @Query(ORDER) sortOrder: String? = null,
        @Query(PAGE) page: Int,
        @Query(PER_PAGE) perPage: Int,
    ): Response<List<PcPartsResponseDto>>

    @GET("pc_parts/{id}")
    suspend fun getDetails(
        @Path("id") id: Int,
    ): Response<PartDetailResponseDto>

    companion object {
        private const val ENDPOINT_PC_PARTS = "pc_parts"
        private const val TITLE_LIKE = "title_like"
        private const val CATEGORY = "category"
        private const val PRICE_GREATER_THAN = "price_gte"
        private const val PRICE_LESS_THAN = "price_lte"
        private const val CONDITION = "condition"
        private const val BRAND = "brand"
        private const val MODEL = "model"
        private const val SORT = "_sort"
        private const val ORDER = "_order"
        private const val PAGE = "_page"
        private const val PER_PAGE = "_per_page"

    }
}