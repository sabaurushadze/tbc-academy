package com.example.academy_tbc.domain.repository.outfit

import com.example.academy_tbc.domain.common.DataError
import com.example.academy_tbc.domain.common.Resource
import com.example.academy_tbc.domain.model.outfit.Outfit

interface OutfitRepository {
    suspend fun getOutfits(): Resource<List<Outfit>, DataError.Network>
    suspend fun getOutfitsByCategory(categoryId: Int): Resource<List<Outfit>, DataError.Network>
}