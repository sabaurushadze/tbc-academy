package com.example.academy_tbc.domain.usecase.login

import com.example.academy_tbc.data.preferences.PreferenceKeys
import com.example.academy_tbc.domain.model.login.LogInResponse
import com.example.academy_tbc.domain.repository.datastore.DataStoreRepository
import com.example.academy_tbc.domain.repository.login.LogInRepository
import com.example.academy_tbc.domain.resource.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class LogInUseCase @Inject constructor(
    private val logInRepository: LogInRepository,
    private val dataStoreRepository: DataStoreRepository
) {
    operator fun invoke(
        email: String, password: String, rememberMe: Boolean,
    ): Flow<Resource<LogInResponse>> {
        return logInRepository.logIn(
            email = email, password = password, rememberMe = rememberMe
        ).onEach { result ->
            if (result is Resource.Success) {
                dataStoreRepository.put(PreferenceKeys.USER_EMAIL, email)

                if (rememberMe) {
                    dataStoreRepository.put(PreferenceKeys.USER_TOKEN, result.data.token)
                }
            }
        }
    }
}