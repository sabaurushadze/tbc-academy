package com.example.academy_tbc.presentation.screen.register_username

import androidx.lifecycle.viewModelScope
import com.example.academy_tbc.presentation.common.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterUsernameViewModel @Inject constructor() :
    BaseViewModel<RegisterUsernameState, RegisterUsernameSideEffect, RegisterUsernameEvent>(
        RegisterUsernameState()
    ) {
    override fun onEvent(event: RegisterUsernameEvent) {
        when (event) {
            RegisterUsernameEvent.Register -> register()
            is RegisterUsernameEvent.UsernameChanged -> updateUsername(event.username)
        }
    }

    private fun register() = viewModelScope.launch {
        updateState { copy(isLoading = true) }
        emitSideEffect(RegisterUsernameSideEffect.NavigateToHome)
        updateState { copy(isLoading = false) }
    }

    private fun updateUsername(username: String) {
        updateState {
            copy(username = username)
        }
    }

}