package com.example.academy_tbc.presentation.screen.register_username

import com.example.academy_tbc.presentation.common.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterUsernameViewModel @Inject constructor() :
    BaseViewModel<RegisterUsernameState, RegisterUsernameSideEffect, RegisterUsernameEvent>(
        RegisterUsernameState()
    ) {

    override fun setLoading(isLoading: Boolean) = updateState { copy(isLoading = isLoading) }

    override fun onEvent(event: RegisterUsernameEvent) {
        when (event) {
            RegisterUsernameEvent.Register -> register()
            is RegisterUsernameEvent.UsernameChanged -> updateUsername(event.username)
        }
    }

    private fun register() = launchWithLoading {
        emitSideEffect(RegisterUsernameSideEffect.NavigateToHome)
    }

    private fun updateUsername(username: String) {
        updateState {
            copy(username = username)
        }
    }

}