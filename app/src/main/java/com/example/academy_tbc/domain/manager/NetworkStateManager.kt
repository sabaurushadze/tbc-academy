package com.example.academy_tbc.domain.manager

import kotlinx.coroutines.flow.Flow

interface NetworkStateManager {
    val isConnected: Flow<Boolean>
}