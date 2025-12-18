package com.example.academy_tbc.domain.usecase.location

import com.example.academy_tbc.domain.common.ApiError
import com.example.academy_tbc.domain.repository.location.LocationRepository
import com.example.academy_tbc.domain.common.Resource
import com.example.academy_tbc.domain.model.location.Location
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLocationUseCase @Inject constructor(
    private val locationRepository: LocationRepository,
) {
    operator fun invoke(): Flow<Resource<List<Location>, ApiError>> {
        return locationRepository.getLocations()
    }
}