package com.example.academy_tbc.presentation.screen.login

import androidx.lifecycle.viewModelScope
import com.example.academy_tbc.domain.resource.Resource
import com.example.academy_tbc.domain.usecase.login.LogInUseCase
import com.example.academy_tbc.presentation.common.view.BaseViewModel
import com.example.academy_tbc.presentation.common.mapper.toMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
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
        viewModelScope.launch {
            logInUseCase(
                email = email, password = password
            ).collectLatest { result ->
                when (result) {
                    is Resource.Loading -> updateState { copy(isLoading = result.isLoading) }
                    is Resource.Success -> sendEffect(LogInSideEffect.NavigateToHome)
                    is Resource.Error -> sendEffect(LogInSideEffect.ShowError(result.error.toMessage()))
                }
            }
        }
    }
}