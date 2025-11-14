package com.example.academy_tbc.presentation.screen.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.academy_tbc.AuthApplication
import com.example.academy_tbc.data.auth.AuthRepository
import com.example.academy_tbc.data.auth.UserTokenRepository
import com.example.academy_tbc.data.auth.register.RequestRegisterDto
import com.example.academy_tbc.presentation.screen.register.state.RegisterField
import com.example.academy_tbc.presentation.screen.register.state.RegisterFieldError
import com.example.academy_tbc.presentation.screen.register.state.RegisterValidationError
import com.example.academy_tbc.presentation.utils.Validations
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okio.IOException

class RegisterViewModel(
    private val networkAuthRepository: AuthRepository,
    private val userTokenRepository: UserTokenRepository
) : ViewModel() {
    private val _registerState = MutableStateFlow<RegisterUiState?>(RegisterUiState.Idle)
    val registerUiState: StateFlow<RegisterUiState?> = _registerState.asStateFlow()

    fun resetState() {
        _registerState.value = null
    }

    private suspend fun saveToken(token: String) {
        userTokenRepository.saveToken(token)
    }

    fun validateRegisterData(
        email: String, password: String, userName: String
    ): Pair<Boolean, Map<RegisterField, RegisterFieldError>> {
        val errors = mutableMapOf<RegisterField, RegisterFieldError>()

        if (!Validations.validateEmail(email)) {
            errors[RegisterField.EMAIL] = RegisterFieldError.INVALID_EMAIL
        }

        if (!Validations.validatePassword(password)) {
            errors[RegisterField.PASSWORD] = RegisterFieldError.INVALID_PASSWORD
        }

        if (!Validations.validateUserName(userName)) {
            errors[RegisterField.USERNAME] = RegisterFieldError.INVALID_USERNAME
        }

        return Pair(errors.isEmpty(), errors)
    }

    fun register(user: RequestRegisterDto) {
        viewModelScope.launch {
            _registerState.value = RegisterUiState.Loading
            try {
                val response = networkAuthRepository.register(user)
                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null) {
                    saveToken(responseBody.token)
                    _registerState.value = RegisterUiState.Success
                } else if (response.code() == 400) {
                    _registerState.value =
                        RegisterUiState.Error(RegisterValidationError.EXCEPTION_USER_NOT_FOUND)
                }
            } catch (e: Exception) {
                when (e) {
                    is IOException -> _registerState.value = RegisterUiState.Error(
                        RegisterValidationError.EXCEPTION_NETWORK
                    )

                    else -> _registerState.value =
                        RegisterUiState.Error(RegisterValidationError.EXCEPTION_UNKNOWN)

                }
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as AuthApplication)
                val authRepository = application.container.authRepository
                val userTokenRepository = application.container.userTokenRepository
                RegisterViewModel(
                    networkAuthRepository = authRepository,
                    userTokenRepository = userTokenRepository
                )
            }
        }
    }
}

sealed interface RegisterUiState {
    object Success : RegisterUiState
    data class Error(val message: RegisterValidationError) : RegisterUiState
    object Loading : RegisterUiState
    object Idle : RegisterUiState
}