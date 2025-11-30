package com.example.academy_tbc.domain.usecase.network

import com.example.academy_tbc.domain.observer.ConnectivityObserver
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveConnectivityUseCase @Inject constructor(
    private val connectivityObserver: ConnectivityObserver
){
    operator fun invoke(): Flow<Boolean> {
        return connectivityObserver.isConnected
    }
}