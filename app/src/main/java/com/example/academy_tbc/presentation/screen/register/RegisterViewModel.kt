package com.example.academy_tbc.presentation.screen.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.academy_tbc.data.auth.AuthRepository
import com.example.academy_tbc.data.auth.register.RequestRegisterDto
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okio.IOException

class RegisterViewModel(
    private val networkAuthRepository: AuthRepository,
) : ViewModel() {
    private val _registerState = MutableStateFlow<RegisterUiState>(RegisterUiState.Success(false))
    val registerUiState: StateFlow<RegisterUiState> = _registerState.asStateFlow()

    private val _navigationEvent = MutableSharedFlow<RegisterNavigationEvent>(replay = 0)
    val navigationEvent = _navigationEvent.asSharedFlow()

    fun resetState() {
        _registerState.value = RegisterUiState.Success(false)
    }

    fun validateRegisterData(
        email: String, password: String
    ): Boolean {
        val isValidEmail = RegisterValidations.validateEmail(email)
        val isValidPassword = RegisterValidations.validatePassword(password)

        return isValidEmail && isValidPassword
    }

    private var registerJob: Job? = null

    fun register(user: RequestRegisterDto) {
        if (registerJob != null) return

        registerJob = viewModelScope.launch {
            try {
                _registerState.value = RegisterUiState.Loading
                val response = networkAuthRepository.register(user)
                val responseBody = response.body()

                if (response.isSuccessful && responseBody != null) {

                    _navigationEvent.emit(RegisterNavigationEvent.NavigateToLogin)
                    _registerState.value = RegisterUiState.Success(true)
                } else if (response.code() == 400) {
                    _registerState.value =
                        RegisterUiState.Error(RegisterValidationError.EXCEPTION_USER_NOT_FOUND)
                }
            } catch (e: Exception) {
                when (e) {
                    is IOException -> _registerState.value =
                        RegisterUiState.Error(RegisterValidationError.EXCEPTION_NETWORK)

                    else -> _registerState.value =
                        RegisterUiState.Error(RegisterValidationError.EXCEPTION_UNKNOWN)
                }
            } finally {
                registerJob = null
            }
        }
    }
}

sealed class RegisterUiState {
    data class Success(val isRegistered: Boolean) : RegisterUiState()
    data class Error(val error: RegisterValidationError) : RegisterUiState()
    object Loading : RegisterUiState()
}

sealed class RegisterNavigationEvent {
    object NavigateToLogin : RegisterNavigationEvent()
}