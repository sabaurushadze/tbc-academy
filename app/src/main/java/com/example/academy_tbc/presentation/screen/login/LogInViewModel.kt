package com.example.academy_tbc.presentation.screen.login

import com.example.academy_tbc.domain.common.onFailure
import com.example.academy_tbc.domain.common.onSuccess
import com.example.academy_tbc.domain.preferences.AppPreferenceKeys
import com.example.academy_tbc.domain.usecase.datastore.SetPreferenceUseCase
import com.example.academy_tbc.domain.usecase.auth.login.LogInUseCase
import com.example.academy_tbc.presentation.common.view.BaseViewModel
import com.example.academy_tbc.presentation.screen.login.mapper.UiAuthTokenMapper
import com.example.academy_tbc.presentation.util.toStringResId
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LogInViewModel @Inject constructor(
    private val logInUseCase: LogInUseCase,
    private val setPreferenceUseCase: SetPreferenceUseCase,
    private val uiAuthTokenMapper: UiAuthTokenMapper,
) : BaseViewModel<LogInState, LogInSideEffect, LogInEvent>(LogInState()) {

    override fun onEvent(event: LogInEvent) {
        when (event) {
            is LogInEvent.LogIn -> logIn(
                email = event.email, password = event.password
            )
        }
    }

    override fun setLoading(isLoading: Boolean) = updateState { copy(isLoading = isLoading) }

    private fun logIn(email: String, password: String) = launchWithLoading {
        logInUseCase(email = email, password = password)
            .onSuccess { resultDomain ->
                val resultUi = uiAuthTokenMapper.mapFromDomain(resultDomain)
                setPreferenceUseCase(AppPreferenceKeys.TOKEN, resultUi.token)
                emitSideEffect(LogInSideEffect.NavigateToHome)
            }
            .onFailure { emitSideEffect(LogInSideEffect.ShowSnackBar(errorRes = it.toStringResId())) }

    }
}