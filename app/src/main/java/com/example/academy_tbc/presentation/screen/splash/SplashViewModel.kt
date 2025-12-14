package com.example.academy_tbc.presentation.screen.splash

import androidx.lifecycle.viewModelScope
import com.example.academy_tbc.domain.usecase.auth.HasUserUseCase
import com.example.academy_tbc.presentation.common.view.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val hasUser: HasUserUseCase,
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
            if (hasUser()) {
                sendEffect(SplashSideEffect.NavigateToHome)
            } else {
                sendEffect(SplashSideEffect.NavigateToOnboarding)
            }
        }
    }

    private fun onStopSplash() {
        splashJob?.cancel()
    }

}