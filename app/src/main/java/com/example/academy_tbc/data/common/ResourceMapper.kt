package com.example.academy_tbc.data.common

import com.example.academy_tbc.domain.common.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

fun <D, R> Resource<D>.map(transform: (D) -> R): Resource<R> {
    return when (this) {
        is Resource.Success -> Resource.Success(transform(data))
        is Resource.Error -> Resource.Error(error)
        is Resource.Loading -> Resource.Loading(isLoading)
    }
}

fun <D, R> Flow<Resource<D>>.mapResource(transform: (D) -> R): Flow<Resource<R>> {
    return this.map { it.map(transform) }
}
