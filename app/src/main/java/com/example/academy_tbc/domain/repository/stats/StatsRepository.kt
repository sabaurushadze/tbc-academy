package com.example.academy_tbc.domain.repository.stats

import com.example.academy_tbc.domain.model.stats.StatsResponse
import com.example.academy_tbc.domain.resource.Resource
import kotlinx.coroutines.flow.Flow

interface StatsRepository {
    fun getStats(): Flow<Resource<List<StatsResponse>>>
}