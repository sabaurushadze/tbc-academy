package com.example.academy_tbc.presentation.screen.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.academy_tbc.data.auth.AuthRepository
import com.example.academy_tbc.data.auth.UserTokenRepository
import com.example.academy_tbc.data.auth.login.RequestLoginDto
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okio.IOException

class LogInViewModel(
    private val networkAuthRepository: AuthRepository,
    private val userTokenRepository: UserTokenRepository
) : ViewModel() {
    private val _loginState = MutableStateFlow<LogInUiState>(LogInUiState.Success(false))
    val loginUiState: StateFlow<LogInUiState> = _loginState.asStateFlow()

    private val _navigationEvent = MutableSharedFlow<LogInNavigationEvent>(replay = 0)
    val navigationEvent = _navigationEvent.asSharedFlow()

    fun resetState() {
        _loginState.value = LogInUiState.Success(false)
    }

    private suspend fun saveToken(token: String) {
        userTokenRepository.saveToken(token)
    }

    private suspend fun saveEmail(email: String) {
        userTokenRepository.saveEmail(email)
    }

    fun validateLogInData(
        email: String, password: String
    ): Boolean {
        val isValidEmail = LogInValidations.validateEmail(email)
        val isValidPassword = LogInValidations.validatePassword(password)

        return isValidEmail && isValidPassword
    }

    private var loginJob: Job? = null

    fun login(
        user: RequestLoginDto, isRemembered: Boolean, email: String
    ) {
        if (loginJob != null) return

        loginJob = viewModelScope.launch {
            try {
                _loginState.value = LogInUiState.Loading
                val response = networkAuthRepository.login(user)
                val responseBody = response.body()

                if (response.isSuccessful && responseBody != null) {
                    if (isRemembered) {
                        saveToken(responseBody.token)
                    }
                    saveEmail(email)

                    _navigationEvent.emit(LogInNavigationEvent.NavigateToHome)
                    _loginState.value = LogInUiState.Success(true)
                } else if (response.code() == 400) {
                    _loginState.value =
                        LogInUiState.Error(LogInValidationError.EXCEPTION_USER_NOT_FOUND)
                }
            } catch (e: Exception) {
                when (e) {
                    is IOException -> _loginState.value =
                        LogInUiState.Error(LogInValidationError.EXCEPTION_NETWORK)

                    else -> _loginState.value =
                        LogInUiState.Error(LogInValidationError.EXCEPTION_UNKNOWN)
                }
            } finally {
                loginJob = null
            }
        }
    }
}

sealed class LogInUiState {
    data class Success(val isLoggedIn: Boolean) : LogInUiState()
    data class Error(val error: LogInValidationError) : LogInUiState()
    object Loading : LogInUiState()
}

sealed class LogInNavigationEvent {
    object NavigateToHome : LogInNavigationEvent()
}