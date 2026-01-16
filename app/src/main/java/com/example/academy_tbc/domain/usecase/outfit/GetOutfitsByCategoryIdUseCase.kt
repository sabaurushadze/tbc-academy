package com.example.academy_tbc.domain.usecase.outfit

import android.util.Log.d
import com.example.academy_tbc.domain.common.DataError
import com.example.academy_tbc.domain.common.Resource
import com.example.academy_tbc.domain.model.outfit.Outfit
import com.example.academy_tbc.domain.repository.outfit.OutfitRepository
import javax.inject.Inject

class GetOutfitsByCategoryIdUseCase @Inject constructor(
    private val outfitRepository: OutfitRepository,
) {
    suspend operator fun invoke(id: Int): Resource<List<Outfit>, DataError.Network> {
        return if (id == 1) {
            d("asdd", "else called with id: $id")
            outfitRepository.getOutfits()
        } else {
            d("asdd", "else called with id: $id")
            outfitRepository.getOutfitsByCategory(id)
        }
    }
}