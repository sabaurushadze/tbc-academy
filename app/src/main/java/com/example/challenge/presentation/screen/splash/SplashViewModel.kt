package com.example.challenge.presentation.screen.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.challenge.domain.usecase.datastore.GetTokenUseCase
import com.example.challenge.presentation.screen.log_in.LogInSideEffect
import com.example.challenge.presentation.screen.log_in.LogInViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject




@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getTokenUseCase: GetTokenUseCase
) : ViewModel() {

    private val _sideEffect by lazy { Channel<SplashSideEffect>() }
    val sideEffect = _sideEffect.receiveAsFlow()

    init {
        readSession()
    }

    private fun readSession() {
        viewModelScope.launch {
            getTokenUseCase().collect {
                if (it.isEmpty())
                    _sideEffect.send(SplashSideEffect.NavigateToLogIn)
                else
                    _sideEffect.send(SplashSideEffect.NavigateToConnections)
            }
        }
    }
}