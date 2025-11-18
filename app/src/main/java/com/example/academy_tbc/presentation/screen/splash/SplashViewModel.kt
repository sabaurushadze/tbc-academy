package com.example.academy_tbc.presentation.screen.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.academy_tbc.data.local.UserDataStore
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class SplashViewModel(
    private val userDataStore: UserDataStore
) : ViewModel() {
    private val _sideEffect = MutableSharedFlow<SplashSideEffect>()
    val sideEffect = _sideEffect.asSharedFlow()

    private var splashJob: Job? = null

    fun onEvent(event: SplashEvent) {
        when (event) {
            SplashEvent.OnStartSplash -> onStartSplash()
            SplashEvent.OnStopSplash -> onStopSplash()
        }
    }

    private fun onStartSplash() {
        splashJob = viewModelScope.launch {
            val userToken = userDataStore.getToken.first()
            if (userToken.isNotEmpty()) {
                _sideEffect.emit(SplashSideEffect.NavigateToHome)
            } else {
                _sideEffect.emit(SplashSideEffect.NavigateToOnboarding)
            }
        }
    }

    private fun onStopSplash() {
        splashJob?.cancel()
    }
}
