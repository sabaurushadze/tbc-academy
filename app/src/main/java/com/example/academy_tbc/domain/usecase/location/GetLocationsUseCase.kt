package com.example.academy_tbc.domain.usecase.location

import com.example.academy_tbc.domain.common.DataError
import com.example.academy_tbc.domain.common.Resource
import com.example.academy_tbc.domain.model.location.Location
import com.example.academy_tbc.domain.repository.location.LocationRepository
import javax.inject.Inject

class GetLocationsUseCase @Inject constructor(
    private val locationRepository: LocationRepository,
) {
    suspend operator fun invoke(): Resource<List<Location>, DataError.Network> {
        return locationRepository.getLocations()
    }
}