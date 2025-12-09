package com.example.academy_tbc.domain.usecase.home.location

import com.example.academy_tbc.domain.model.home.location.Location
import com.example.academy_tbc.domain.repository.home.location.LocationRepository
import com.example.academy_tbc.domain.resource.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLocationsUseCase @Inject constructor(
    val locationRepository: LocationRepository,
) {
    operator fun invoke(): Flow<Resource<List<Location>>> {
        return locationRepository.getLocations()
    }
}