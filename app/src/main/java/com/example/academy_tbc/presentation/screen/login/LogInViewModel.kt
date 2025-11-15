package com.example.academy_tbc.presentation.screen.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.academy_tbc.data.auth.AuthRepository
import com.example.academy_tbc.data.auth.UserTokenRepository
import com.example.academy_tbc.data.auth.login.RequestLoginDto
import com.example.academy_tbc.presentation.screen.login.state.LogInField
import com.example.academy_tbc.presentation.screen.login.state.LogInFieldError
import com.example.academy_tbc.presentation.screen.login.state.LogInValidationError
import com.example.academy_tbc.presentation.utils.Validations
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okio.IOException

class LogInViewModel(
    private val networkAuthRepository: AuthRepository,
    private val userTokenRepository: UserTokenRepository
) : ViewModel() {
    private val _loginState = MutableStateFlow(LogInUiState())
    val loginUiState: StateFlow<LogInUiState> = _loginState.asStateFlow()

    fun resetState() {
        _loginState.update { it.copy(isLoading = false, isLoggedIn = null, error = null) }
    }

    private suspend fun saveToken(token: String) {
        userTokenRepository.saveToken(token)
    }

    fun validateLogInData(
        email: String, password: String
    ): Pair<Boolean, Map<LogInField, LogInFieldError>> {
        val errors = mutableMapOf<LogInField, LogInFieldError>()

        if (!Validations.validateEmail(email)) {
            errors[LogInField.EMAIL] = LogInFieldError.INVALID_EMAIL
        }

        if (!Validations.validatePassword(password)) {
            errors[LogInField.PASSWORD] = LogInFieldError.INVALID_PASSWORD
        }
        return Pair(errors.isEmpty(), errors)
    }

    private var loginJob: Job? = null

    fun login(user: RequestLoginDto) {
        if (loginJob != null) return

        loginJob = viewModelScope.launch {
            try {
                _loginState.update { it.copy(isLoading = true) }
                val response = networkAuthRepository.login(user)
                val responseBody = response.body()

                if (response.isSuccessful && responseBody != null) {
                    saveToken(responseBody.token)
                    _loginState.update { it.copy(isLoggedIn = true) }
                } else if (response.code() == 400) {
                    _loginState.update {
                        it.copy(error = LogInValidationError.EXCEPTION_USER_NOT_FOUND)
                    }
                }
            } catch (e: Exception) {
                when (e) {
                    is IOException -> _loginState.update {
                        it.copy(error = LogInValidationError.EXCEPTION_NETWORK)
                    }

                    else -> _loginState.update {
                        it.copy(error = LogInValidationError.EXCEPTION_UNKNOWN)
                    }

                }
            } finally {
                loginJob = null
            }
        }
    }
}

data class LogInUiState(
    val isLoading: Boolean = false,
    val error: LogInValidationError? = null,
    val isLoggedIn: Boolean? = null
)
