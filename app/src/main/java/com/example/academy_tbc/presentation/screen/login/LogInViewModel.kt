package com.example.academy_tbc.presentation.screen.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.academy_tbc.AuthApplication
import com.example.academy_tbc.data.auth.AuthRepository
import com.example.academy_tbc.data.auth.UserTokenRepository
import com.example.academy_tbc.data.auth.login.RequestLoginDto
import com.example.academy_tbc.presentation.utils.Validations
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException

class LogInViewModel(
    private val networkAuthRepository: AuthRepository,
    private val userTokenRepository: UserTokenRepository
) : ViewModel() {
    private val _loginState = MutableStateFlow<LogInUiState>(LogInUiState.Idle)
    val loginUiState: StateFlow<LogInUiState> = _loginState.asStateFlow()


    fun resetState() {
        _loginState.value = LogInUiState.Idle
    }

    private fun saveToken(token: String) {
        viewModelScope.launch {
            userTokenRepository.saveToken(token)
        }
    }

    fun validateLogInData(
        email: String, password: String
    ): Pair<Boolean, Map<LogInField, LogInFieldErrors>> {
        val errors = mutableMapOf<LogInField, LogInFieldErrors>()

        if (!Validations.validateEmail(email)) {
            errors[LogInField.EMAIL] = LogInFieldErrors.INVALID_EMAIL
        }

        if (!Validations.validatePassword(password)) {
            errors[LogInField.PASSWORD] = LogInFieldErrors.INVALID_PASSWORD
        }
        return Pair(errors.isEmpty(), errors)
    }

    fun login(user: RequestLoginDto) {
        viewModelScope.launch {
            _loginState.value = LogInUiState.Loading
            try {
                val response = networkAuthRepository.login(user)
                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null) {
                    saveToken(responseBody.token)
                    _loginState.value = LogInUiState.Success
                } else if (response.code() == 400) {
                    _loginState.value =
                        LogInUiState.Error(LogInExceptionErrors.EXCEPTION_USER_NOT_FOUND)
                }
            } catch (_: IOException) {
                _loginState.value = LogInUiState.Error(LogInExceptionErrors.EXCEPTION_NETWORK)
            } catch (_: HttpException) {
                _loginState.value = LogInUiState.Error(LogInExceptionErrors.EXCEPTION_CREDENTIALS)
            } catch (_: Exception) {
                _loginState.value = LogInUiState.Error(LogInExceptionErrors.EXCEPTION_UNKNOWN)
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as AuthApplication)
                val authRepository = application.container.authRepository
                val userTokenRepository = application.container.userTokenRepository
                LogInViewModel(
                    networkAuthRepository = authRepository,
                    userTokenRepository = userTokenRepository
                )
            }
        }
    }
}

sealed interface LogInUiState {
    object Success : LogInUiState
    data class Error(val message: LogInExceptionErrors) : LogInUiState
    object Loading : LogInUiState
    object Idle : LogInUiState
}
