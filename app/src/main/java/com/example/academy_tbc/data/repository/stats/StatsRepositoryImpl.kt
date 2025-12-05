package com.example.academy_tbc.data.repository.stats

import com.example.academy_tbc.data.common.ResponseHandler
import com.example.academy_tbc.data.common.asResource
import com.example.academy_tbc.data.mapper.network.toDomain
import com.example.academy_tbc.data.service.stats.StatsApiService
import com.example.academy_tbc.domain.model.stats.StatsResponse
import com.example.academy_tbc.domain.repository.stats.StatsRepository
import com.example.academy_tbc.domain.resource.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import kotlin.collections.map

class StatsRepositoryImpl @Inject constructor(
    private val responseHandler: ResponseHandler,
    private val authService: StatsApiService,
) : StatsRepository {
    override fun getStats(): Flow<Resource<List<StatsResponse>>> {
        return responseHandler.safeApiCall {
            authService.getStatistics()
        }.asResource { list ->
            list.map { it.toDomain() }
        }
    }
}
