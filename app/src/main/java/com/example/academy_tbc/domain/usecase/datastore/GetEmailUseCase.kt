package com.example.academy_tbc.domain.usecase.datastore

import com.example.academy_tbc.data.preferences.PreferenceKeys
import com.example.academy_tbc.domain.repository.datastore.DataStoreRepository
import javax.inject.Inject

class GetEmailUseCase @Inject constructor(
    private val dataStoreRepository: DataStoreRepository,
) {
    suspend operator fun invoke(): String {
        return dataStoreRepository.getFirstPreference(PreferenceKeys.USER_EMAIL, "")
    }
}