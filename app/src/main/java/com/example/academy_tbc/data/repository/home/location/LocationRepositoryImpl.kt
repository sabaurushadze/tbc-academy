package com.example.academy_tbc.data.repository.home.location

import com.example.academy_tbc.data.mapper.home.location.toDomain
import com.example.academy_tbc.data.room.home.location.LocationDao
import com.example.academy_tbc.data.room.home.location.LocationEntity
import com.example.academy_tbc.data.service.home.LocationApiService
import com.example.academy_tbc.domain.common.AppError
import com.example.academy_tbc.domain.model.home.location.Location
import com.example.academy_tbc.domain.observer.ConnectivityObserver
import com.example.academy_tbc.domain.repository.home.location.LocationRepository
import com.example.academy_tbc.domain.resource.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import java.io.IOException
import java.net.SocketTimeoutException
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(
    private val authService: LocationApiService,
    private val locationDao: LocationDao,
    private val connectivityObserver: ConnectivityObserver,
) : LocationRepository {
    override fun getLocations(): Flow<Resource<List<Location>>> = flow {
        emit(Resource.Loading(true))

        val isConnected = connectivityObserver.isConnected.first()
        if (!isConnected) {
            emit(Resource.Error(AppError.Network))
            emit(Resource.Loading(false))
            return@flow
        }

        try {
            val response = authService.getLocations()

            if (response.isSuccessful) {
                val apiLocations = response.body() ?: emptyList()

                val entities = apiLocations.map { dto ->
                    LocationEntity(
                        title = dto.title,
                        cover = dto.cover
                    )
                }
                locationDao.insertUsers(*entities.toTypedArray())

                emit(Resource.Success(apiLocations.map { it.toDomain() }))
            } else {
                emit(Resource.Error(AppError.Server(response.code())))
            }

        } catch (e: Throwable) {
            val appError = when (e) {
                is SocketTimeoutException -> AppError.Network
                is IOException -> AppError.Network
                else -> AppError.Unknown
            }
            emit(Resource.Error(error = appError))
        } finally {
            emit(Resource.Loading(isLoading = false))
        }
    }
}