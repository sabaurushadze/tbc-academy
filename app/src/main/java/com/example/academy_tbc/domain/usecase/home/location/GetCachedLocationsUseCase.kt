package com.example.academy_tbc.domain.usecase.home.location

import com.example.academy_tbc.domain.model.home.location.Location
import com.example.academy_tbc.domain.repository.home.location.LocationRepository
import com.example.academy_tbc.domain.resource.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCachedLocationsUseCase @Inject constructor(
    val locationRepository: LocationRepository,
) {
    operator fun invoke(): Flow<List<Location>> {
        return locationRepository.getCachedLocations()
    }
}