package com.example.academy_tbc.presentation.screen.login

import androidx.lifecycle.viewModelScope
import com.example.academy_tbc.domain.common.onFailure
import com.example.academy_tbc.domain.common.onSuccess
import com.example.academy_tbc.domain.usecase.auth.login.LogInWithEmailAndPasswordUseCase
import com.example.academy_tbc.presentation.common.BaseViewModel
import com.example.academy_tbc.presentation.util.toStringResId
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LogInViewModel @Inject constructor(
    private val logInUseCase: LogInWithEmailAndPasswordUseCase,
) : BaseViewModel<LogInState, LogInSideEffect, LogInEvent>(LogInState()) {

    override fun onEvent(event: LogInEvent) {
        when (event) {
            is LogInEvent.LogIn -> logIn()
            is LogInEvent.EmailChanged -> updateEmail(event.email)
            is LogInEvent.PasswordChanged -> updatePassword(event.password)
        }
    }

    private fun logIn() = viewModelScope.launch {
        updateState { copy(isLoading = true) }
        logInUseCase(email = state.value.email, password = state.value.password)
            .onSuccess {
                emitSideEffect(LogInSideEffect.NavigateToHome)
                updateState { copy(isLoading = false) }

            }
            .onFailure {
                emitSideEffect(LogInSideEffect.ShowSnackBar(errorRes = it.toStringResId()))
                updateState { copy(isLoading = false) }
            }
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