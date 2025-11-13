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
import com.example.academy_tbc.presentation.utils.Validations
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException

class RegisterViewModel(
    private val networkAuthRepository: AuthRepository,
    private val userTokenRepository: UserTokenRepository
) : ViewModel() {
    private val _registerState = MutableStateFlow<RegisterUiState>(RegisterUiState.Idle)
    val registerUiState: StateFlow<RegisterUiState> = _registerState.asStateFlow()

    fun resetState() {
        _registerState.value = RegisterUiState.Idle
    }

    private fun saveToken(token: String) {
        viewModelScope.launch {
            userTokenRepository.saveToken(token)
        }
    }

    fun validateRegisterData(
        email: String, password: String, userName: String
    ): Pair<Boolean, Map<RegisterField, RegisterFieldErrors>> {
        val errors = mutableMapOf<RegisterField, RegisterFieldErrors>()

        if (!Validations.validateEmail(email)) {
            errors[RegisterField.EMAIL] = RegisterFieldErrors.INVALID_EMAIL
        }

        if (!Validations.validatePassword(password)) {
            errors[RegisterField.PASSWORD] = RegisterFieldErrors.INVALID_PASSWORD
        }

        if (!Validations.validateUserName(userName)) {
            errors[RegisterField.USERNAME] = RegisterFieldErrors.INVALID_USERNAME
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
                        RegisterUiState.Error(RegisterExceptionErrors.EXCEPTION_USER_NOT_FOUND)
                }
            } catch (_: IOException) {
                _registerState.value =
                    RegisterUiState.Error(RegisterExceptionErrors.EXCEPTION_NETWORK)
            } catch (_: HttpException) {
                _registerState.value =
                    RegisterUiState.Error(RegisterExceptionErrors.EXCEPTION_CREDENTIALS)
            } catch (_: Exception) {
                _registerState.value =
                    RegisterUiState.Error(RegisterExceptionErrors.EXCEPTION_UNKNOWN)
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
    data class Error(val message: RegisterExceptionErrors) : RegisterUiState
    object Loading : RegisterUiState
    object Idle : RegisterUiState
}