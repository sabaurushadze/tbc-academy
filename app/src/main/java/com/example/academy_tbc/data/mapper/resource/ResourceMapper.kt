package com.example.academy_tbc.data.mapper.resource

import com.example.academy_tbc.domain.resource.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

fun <DTO, DOMAIN> Flow<Resource<DTO>>.asResource(
    onSuccess: (DTO) -> DOMAIN,
): Flow<Resource<DOMAIN>> {
    return this.map { resource ->
        when (resource) {
            is Resource.Error -> Resource.Error(error = resource.error)
            is Resource.Loading -> Resource.Loading(isLoading = resource.isLoading)
            is Resource.Success -> Resource.Success(data = onSuccess(resource.data))
        }
    }
}