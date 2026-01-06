package com.example.academy_tbc.presentation.screen.splash

import androidx.lifecycle.viewModelScope
import com.example.academy_tbc.domain.common.onFailure
import com.example.academy_tbc.domain.common.onSuccess
import com.example.academy_tbc.domain.usecase.auth.splash.CheckUserAuthStateUseCase
import com.example.academy_tbc.presentation.common.view.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val checkUserAuthState: CheckUserAuthStateUseCase,
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
            checkUserAuthState()
                .onSuccess { emitSideEffect(SplashSideEffect.NavigateToHome) }
                .onFailure { emitSideEffect(SplashSideEffect.NavigateToLogin) }
        }
    }

    private fun onStopSplash() {
        splashJob?.cancel()
    }
}