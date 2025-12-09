package com.example.academy_tbc.domain.repository.home

import com.example.academy_tbc.domain.model.location.Location
import com.example.academy_tbc.domain.resource.Resource
import kotlinx.coroutines.flow.Flow

interface LocationRepository {
    fun getLocations(): Flow<Resource<List<Location>>>
}