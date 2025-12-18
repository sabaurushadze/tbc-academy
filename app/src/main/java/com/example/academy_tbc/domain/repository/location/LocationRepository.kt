package com.example.academy_tbc.domain.repository.location

import com.example.academy_tbc.domain.common.ApiError
import com.example.academy_tbc.domain.common.Resource
import com.example.academy_tbc.domain.model.location.Location
import kotlinx.coroutines.flow.Flow

interface LocationRepository {
    fun getLocations(): Flow<Resource<List<Location>, ApiError>>
}