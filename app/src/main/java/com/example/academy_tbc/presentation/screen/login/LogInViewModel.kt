package com.example.academy_tbc.presentation.screen.login

import androidx.lifecycle.viewModelScope
import com.example.academy_tbc.domain.resource.Resource
import com.example.academy_tbc.domain.usecase.login.LogInUseCase
import com.example.academy_tbc.domain.usecase.validations.EmailValidationUseCase
import com.example.academy_tbc.domain.usecase.validations.PasswordValidationUseCase
import com.example.academy_tbc.presentation.common.mapper.toMessage
import com.example.academy_tbc.presentation.common.view.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LogInViewModel @Inject constructor(
    private val logInUseCase: LogInUseCase,
    private val emailValidationUseCase: EmailValidationUseCase,
    private val passwordValidationUseCase: PasswordValidationUseCase,
) : BaseViewModel<LogInState, LogInSideEffect, LogInEvent>(LogInState()) {


    override fun onEvent(event: LogInEvent) {
        when (event) {
            is LogInEvent.LogIn -> logIn(
                email = event.email, password = event.password, isRemembered = event.isRemembered
            )

            is LogInEvent.EmailChanged -> updateEmail(event.email)
            is LogInEvent.PasswordChanged -> updatePassword(event.password)
            is LogInEvent.RememberMeChanged -> updateRememberMe(event.isRemembered)
        }
    }

    private fun updateEmail(email: String) {
        updateState {
            val enabled = validateInputs(email = email, password = password)
            copy(email = email, isLoginEnabled = enabled)
        }
    }

    private fun updatePassword(password: String) {
        updateState {
            val enabled = validateInputs(email = email, password = password)
            copy(password = password, isLoginEnabled = enabled)
        }
    }

    private fun updateRememberMe(isRemembered: Boolean) {
        updateState {
            copy(isRemembered = isRemembered)
        }
    }

    private fun validateInputs(email: String, password: String) =
        emailValidationUseCase(email) && passwordValidationUseCase(password)

    private fun logIn(
        email: String, password: String, isRemembered: Boolean,
    ) {
        viewModelScope.launch {
            logInUseCase(
                email = email, password = password, rememberMe = isRemembered
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