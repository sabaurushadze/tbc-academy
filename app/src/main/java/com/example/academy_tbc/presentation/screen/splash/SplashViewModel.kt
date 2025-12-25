package com.example.academy_tbc.presentation.screen.splash

import androidx.lifecycle.viewModelScope
import com.example.academy_tbc.domain.preferences.AppPreferencesKeys
import com.example.academy_tbc.domain.usecase.datastore.GetPreferenceUseCase
import com.example.academy_tbc.presentation.common.view.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getPreferenceUseCase: GetPreferenceUseCase
) : BaseViewModel<Unit, SplashSideEffect, SplashEvent>(Unit) {

    private var splashJob: Job? = null

    override fun onEvent(event: SplashEvent) {
        when (event) {
            SplashEvent.OnStartSplash -> onStartSplash()
            SplashEvent.OnStopSplash -> onStopSplash()
        }
    }

    private fun onStartSplash() {
        splashJob = viewModelScope.launch {
            val token = getPreferenceUseCase.invoke(AppPreferencesKeys.TOKEN, "").first()
            if (token.isEmpty()) {
                emitSideEffect(SplashSideEffect.NavigateToSignIn)
            } else {
                emitSideEffect(SplashSideEffect.NavigateToHome)
            }
        }
    }

    private fun onStopSplash() {
        splashJob?.cancel()
    }

}