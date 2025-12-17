package com.example.academy_tbc.presentation.screen.login

import com.example.academy_tbc.domain.usecase.login.LogInUseCase
import com.example.academy_tbc.presentation.common.mapper.toGenericString
import com.example.academy_tbc.presentation.common.view.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LogInViewModel @Inject constructor(
    private val logInUseCase: LogInUseCase,
) : BaseViewModel<LogInState, LogInSideEffect, LogInEvent>(LogInState()) {


    override fun onEvent(event: LogInEvent) {
        when (event) {
            is LogInEvent.LogIn -> logIn(
                email = event.email, password = event.password
            )
        }
    }

    private fun logIn(
        email: String, password: String,
    ) {
        launchResource(
            flow = logInUseCase(email, password),
            onLoading = { updateState { copy(isLoading = isLoading) } },
            onSuccess = { sendEffect(LogInSideEffect.NavigateToHome) },
            onError = { sendEffect(LogInSideEffect.ShowError(it.toGenericString())) }
        )
    }
}