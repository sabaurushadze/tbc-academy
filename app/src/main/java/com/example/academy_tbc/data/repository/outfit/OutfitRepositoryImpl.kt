package com.example.academy_tbc.data.repository.outfit

import com.example.academy_tbc.data.common.ApiResponseHandler
import com.example.academy_tbc.data.remote.mapper.outfit.toDomain
import com.example.academy_tbc.data.remote.service.outfit.OutfitService
import com.example.academy_tbc.domain.common.DataError
import com.example.academy_tbc.domain.common.Resource
import com.example.academy_tbc.domain.common.mapList
import com.example.academy_tbc.domain.model.outfit.Outfit
import com.example.academy_tbc.domain.repository.outfit.OutfitRepository
import javax.inject.Inject

class OutfitRepositoryImpl @Inject constructor(
    private val apiResponseHandler: ApiResponseHandler,
    private val outfitService: OutfitService,
) : OutfitRepository {
    override suspend fun getOutfits(): Resource<List<Outfit>, DataError.Network> {
        return apiResponseHandler.safeApiCall {
            outfitService.getOutfits()
        }.mapList { it.toDomain() }
    }

    override suspend fun getOutfitsByCategory(categoryId: Int): Resource<List<Outfit>, DataError.Network> {
        return apiResponseHandler.safeApiCall {
            outfitService.getOutfitsByCategory(categoryId)
        }.mapList { it.toDomain() }
    }
}