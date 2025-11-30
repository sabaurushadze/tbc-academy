package com.example.academy_tbc.presentation.screen.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.academy_tbc.domain.resource.Resource
import com.example.academy_tbc.domain.usecase.login.LogInUseCase
import com.example.academy_tbc.domain.usecase.validations.EmailValidationUseCase
import com.example.academy_tbc.domain.usecase.validations.PasswordValidationUseCase
import com.example.academy_tbc.presentation.common.mapper.toMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class LogInViewModel @Inject constructor(
    private val logInUseCase: LogInUseCase,
    private val emailValidationUseCase: EmailValidationUseCase,
    private val passwordValidationUseCase: PasswordValidationUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(LogInState())
    val state: StateFlow<LogInState> = _state.asStateFlow()

    private val _sideEffect = MutableSharedFlow<LogInSideEffect>()
    val sideEffect = _sideEffect.asSharedFlow()

    fun onEvent(event: LogInEvent) {
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
        _state.update { current ->
            val isLoginEnabled = validateInputs(email = email, password = current.password)
            current.copy(email = email, isLoginEnabled = isLoginEnabled)
        }
    }

    private fun updatePassword(password: String) {
        _state.update { current ->
            val isLoginEnabled = validateInputs(email = current.email, password = password)
            current.copy(password = password, isLoginEnabled = isLoginEnabled)
        }
    }

    private fun updateRememberMe(isRemembered: Boolean) {
        _state.update { it.copy(isRemembered = isRemembered) }
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
                    is Resource.Loading -> _state.update { it.copy(isLoading = result.isLoading) }
                    is Resource.Success -> {
                        _sideEffect.emit(LogInSideEffect.NavigateToHome)
                    }

                    is Resource.Error -> _sideEffect.emit(
                        LogInSideEffect.ShowError(result.error.toMessage())
                    )
                }
            }
        }
    }
}