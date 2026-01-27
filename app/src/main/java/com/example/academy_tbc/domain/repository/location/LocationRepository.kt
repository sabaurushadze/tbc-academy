package com.example.academy_tbc.domain.repository.location

import com.example.academy_tbc.domain.common.DataError
import com.example.academy_tbc.domain.common.Resource
import com.example.academy_tbc.domain.model.location.Location

interface LocationRepository {
    suspend fun getLocations(): Resource<List<Location>, DataError.Network>
}