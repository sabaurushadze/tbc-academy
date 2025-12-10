package com.example.academy_tbc.domain.repository.home.location

import com.example.academy_tbc.domain.model.home.location.Location
import com.example.academy_tbc.domain.model.home.post.Post
import com.example.academy_tbc.domain.resource.Resource
import kotlinx.coroutines.flow.Flow

interface LocationRepository {
    fun getLocations(): Flow<Resource<List<Location>>>
    fun getCachedLocations(): Flow<List<Location>>
}