package com.example.academy_tbc.presentation.screen.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.academy_tbc.data.common.Result
import com.example.academy_tbc.data.repository.RegisterRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegisterViewModel() : ViewModel() {
    private val _state = MutableStateFlow(RegisterState())
    val state: StateFlow<RegisterState> = _state.asStateFlow()

    private val _sideEffect = MutableSharedFlow<RegisterSideEffect>()
    val sideEffect = _sideEffect.asSharedFlow()

    fun onEvent(event: RegisterEvent) {
        when (event) {
            is RegisterEvent.Register -> register(
                email = event.email, password = event.password
            )

            is RegisterEvent.EmailChanged -> updateEmail(event.email)
            is RegisterEvent.PasswordChanged -> updatePassword(event.password)
            is RegisterEvent.RepeatPasswordChanged -> updateRepeatPassword(event.repeatPassword)
            RegisterEvent.BackPressed -> viewModelScope.launch {
                _sideEffect.emit(RegisterSideEffect.NavigateBack)
            }
        }
    }

    private fun updateEmail(email: String) {
        _state.update { current ->
            val isRegisterEnabled = validateInputs(
                email = email, password = current.password
            ) && current.password == current.repeatPassword
            current.copy(email = email, isRegisterEnabled = isRegisterEnabled)
        }
    }

    private fun updatePassword(password: String) {
        _state.update { current ->
            val isRegisterEnabled = validateInputs(
                email = current.password, password = password
            ) && password == current.repeatPassword
            current.copy(password = password, isRegisterEnabled = isRegisterEnabled)
        }
    }

    private fun updateRepeatPassword(repeatPassword: String) {
        _state.update { current ->
            val isRegisterEnabled = validateInputs(
                email = current.email, password = repeatPassword
            ) && current.password == repeatPassword
            current.copy(repeatPassword = repeatPassword, isRegisterEnabled = isRegisterEnabled)
        }
    }

    private fun validateInputs(email: String, password: String) =
        RegisterValidations.validateEmail(email) && RegisterValidations.validatePassword(password)

    fun register(email: String, password: String) {
        viewModelScope.launch {
            RegisterRepository.register(email = email, password = password).collect { result ->
                when (result) {
                    is Result.Success -> {
                        _sideEffect.emit(
                            RegisterSideEffect.NavigateToLogin(
                                email = email, password = password
                            )
                        )
                    }

                    is Result.Error -> _sideEffect.emit(RegisterSideEffect.ShowError(result.errorMessage))
                    is Result.Loading -> _state.update { it.copy(isLoading = result.isLoading) }
                }
            }
        }
    }
}
