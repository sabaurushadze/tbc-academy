package com.example.academy_tbc.presentation.screen.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.academy_tbc.data.local.preferences.PreferenceKeys
import com.example.academy_tbc.data.local.repository.DataStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository,

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
            val userToken = dataStoreRepository.getFirstPreference(PreferenceKeys.USER_TOKEN, "")
            if (userToken.isEmpty()) {
                _sideEffect.emit(SplashSideEffect.NavigateToOnboarding)
            } else {
                _sideEffect.emit(SplashSideEffect.NavigateToHome)
            }
        }
    }

    private fun onStopSplash() {
        splashJob?.cancel()
    }
}
