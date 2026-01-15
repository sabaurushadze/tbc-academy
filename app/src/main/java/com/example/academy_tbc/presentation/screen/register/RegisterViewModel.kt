package com.example.academy_tbc.presentation.screen.register

import com.example.academy_tbc.domain.common.onFailure
import com.example.academy_tbc.domain.common.onSuccess
import com.example.academy_tbc.domain.usecase.auth.register.RegisterWithEmailAndPasswordUseCase
import com.example.academy_tbc.presentation.common.BaseViewModel
import com.example.academy_tbc.presentation.util.toStringResId
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterWithEmailAndPasswordUseCase,
) : BaseViewModel<RegisterState, RegisterSideEffect, RegisterEvent>(RegisterState()) {

    override fun setLoading(isLoading: Boolean) = updateState { copy(isLoading = isLoading) }

    override fun onEvent(event: RegisterEvent) {
        when (event) {
            is RegisterEvent.EmailChanged -> updateEmail(event.email)
            is RegisterEvent.PasswordChanged -> updatePassword(event.password)
            RegisterEvent.Register -> register()
        }
    }

    private fun register() = launchWithLoading {
        registerUseCase(email = state.value.email, password = state.value.password)
            .onSuccess {
                emitSideEffect(RegisterSideEffect.NavigateToUserNameCreation)
            }
            .onFailure { emitSideEffect(RegisterSideEffect.ShowSnackBar(errorRes = it.toStringResId())) }
    }

    private fun updateEmail(email: String) {
        updateState {
            copy(email = email)
        }
    }

    private fun updatePassword(password: String) {
        updateState {
            copy(password = password)
        }
    }
}