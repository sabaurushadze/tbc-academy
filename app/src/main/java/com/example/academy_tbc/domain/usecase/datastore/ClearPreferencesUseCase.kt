package com.example.academy_tbc.domain.usecase.datastore

import com.example.academy_tbc.domain.repository.datastore.DataStoreRepository
import javax.inject.Inject

class ClearPreferencesUseCase @Inject constructor(
    private val dataStoreRepository: DataStoreRepository,
) {
    suspend operator fun invoke() {
        return dataStoreRepository.clearAll()
    }
}