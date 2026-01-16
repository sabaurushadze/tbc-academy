package com.example.academy_tbc.domain.usecase.outfit

import com.example.academy_tbc.domain.common.DataError
import com.example.academy_tbc.domain.common.Resource
import com.example.academy_tbc.domain.model.outfit.Outfit
import com.example.academy_tbc.domain.repository.outfit.OutfitRepository
import javax.inject.Inject

class GetOutfitsUseCase @Inject constructor(
    private val outfitRepository: OutfitRepository,
) {
    suspend operator fun invoke(): Resource<List<Outfit>, DataError.Network> {
        return outfitRepository.getOutfits()
    }
}