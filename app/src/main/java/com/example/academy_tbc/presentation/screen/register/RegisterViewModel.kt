package com.example.academy_tbc.presentation.screen.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.academy_tbc.data.auth.AuthRepository
import com.example.academy_tbc.data.auth.UserTokenRepository
import com.example.academy_tbc.data.auth.register.RequestRegisterDto
import com.example.academy_tbc.presentation.screen.register.state.RegisterField
import com.example.academy_tbc.presentation.screen.register.state.RegisterFieldError
import com.example.academy_tbc.presentation.screen.register.state.RegisterValidationError
import com.example.academy_tbc.presentation.utils.Validations
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okio.IOException

class RegisterViewModel(
    private val networkAuthRepository: AuthRepository,
    private val userTokenRepository: UserTokenRepository
) : ViewModel() {
    private val _registerState = MutableStateFlow(RegisterUiState())
    val registerUiState: StateFlow<RegisterUiState> = _registerState.asStateFlow()

    fun resetState() {
        _registerState.update { it.copy(isLoading = false, isRegistered = null, error = null) }
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

    private var registerJob: Job? = null

    fun register(user: RequestRegisterDto) {
        if (registerJob != null) return

        registerJob = viewModelScope.launch {
            try {
                _registerState.update { it.copy(isLoading = true) }
                val response = networkAuthRepository.register(user)
                val responseBody = response.body()

                if (response.isSuccessful && responseBody != null) {
                    saveToken(responseBody.token)
                    _registerState.update { it.copy(isRegistered = true) }
                } else if (response.code() == 400) {
                    _registerState.update {
                        it.copy(error = RegisterValidationError.EXCEPTION_USER_NOT_FOUND)
                    }
                }
            } catch (e: Exception) {
                when (e) {
                    is IOException -> _registerState.update {
                        it.copy(error = RegisterValidationError.EXCEPTION_NETWORK)

                    }

                    else -> _registerState.update {
                        it.copy(error = RegisterValidationError.EXCEPTION_UNKNOWN)
                    }
                }
            } finally {
                registerJob = null
            }
        }
    }
}

data class RegisterUiState(
    val isLoading: Boolean = false,
    val error: RegisterValidationError? = null,
    val isRegistered: Boolean? = null
)