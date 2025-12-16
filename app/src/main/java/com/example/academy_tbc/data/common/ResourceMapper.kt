package com.example.academy_tbc.data.common

import com.example.academy_tbc.domain.common.Resource
import com.example.academy_tbc.domain.common.ResourceError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

fun <D, E : ResourceError, R> Resource<D, E>.map(transform: (D) -> R): Resource<R, E> {
    return when (this) {
        is Resource.Success -> Resource.Success(transform(data))
        is Resource.Error -> Resource.Error(error)
        Resource.Loading -> Resource.Loading
    }
}

fun <D, E : ResourceError, R> Flow<Resource<D, E>>.mapResource(transform: (D) -> R): Flow<Resource<R, E>> {
    return this.map { it.map(transform) }
}