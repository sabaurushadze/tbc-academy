package com.example.academy_tbc.domain.common

interface Failure

sealed interface Resource<out D, out E: Failure> {
    data class Success<out D>(val data: D): Resource<D, Nothing>
    data class Error<out E: Failure>(val error: E): Resource<Nothing, E>
    data object Loading : Resource<Nothing, Nothing>
}