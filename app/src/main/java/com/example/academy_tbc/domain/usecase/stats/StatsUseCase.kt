package com.example.academy_tbc.domain.usecase.stats

import com.example.academy_tbc.domain.model.stats.StatsResponse
import com.example.academy_tbc.domain.repository.stats.StatsRepository
import com.example.academy_tbc.domain.resource.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class StatsUseCase @Inject constructor(
    private val statsRepository: StatsRepository,
) {
    operator fun invoke(): Flow<Resource<List<StatsResponse>>> {
        return statsRepository.getStats()
    }
}