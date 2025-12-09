package com.example.academy_tbc.data.repository.login

import com.example.academy_tbc.data.common.ResponseHandler
import com.example.academy_tbc.data.common.asResource
import com.example.academy_tbc.data.mapper.home.toDomain
import com.example.academy_tbc.data.service.home.LocationApiService
import com.example.academy_tbc.domain.model.location.Location
import com.example.academy_tbc.domain.repository.home.LocationRepository
import com.example.academy_tbc.domain.resource.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LogInRepositoryImpl @Inject constructor(
    private val responseHandler: ResponseHandler,
    private val authService: LocationApiService,
) : LocationRepository {
    override fun getLocations(): Flow<Resource<List<Location>>> {
        return responseHandler.safeApiCall {
            authService.getLocations()
        }.asResource { locationList ->
            locationList.map { it.toDomain() }
        }
    }
}
