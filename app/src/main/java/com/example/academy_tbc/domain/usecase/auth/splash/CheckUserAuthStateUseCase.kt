package com.example.academy_tbc.domain.usecase.auth.splash

import com.example.academy_tbc.domain.common.Resource
import com.example.academy_tbc.domain.common.SessionError
import com.example.academy_tbc.domain.common.asFailure
import com.example.academy_tbc.domain.common.asSuccess
import com.example.academy_tbc.domain.preferences.AppPreferenceKeys
import com.example.academy_tbc.domain.usecase.datastore.GetPreferenceUseCase
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class CheckUserAuthStateUseCase @Inject constructor(
    private val getPreferenceUseCase: GetPreferenceUseCase,
) {
    suspend operator fun invoke(): Resource<Unit, SessionError> {
        val token = getPreferenceUseCase(AppPreferenceKeys.TOKEN, "").first()
        return if (token.isEmpty()) {
            SessionError.Unauthenticated.asFailure()
        } else {
            Unit.asSuccess()
        }
    }
}