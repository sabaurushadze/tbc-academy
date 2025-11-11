package com.example.academy_tbc.screen.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.academy_tbc.AuthApplication
import com.example.academy_tbc.data.AuthRepository
import com.example.academy_tbc.utils.Validations
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException

class LogInViewModel(private val networkAuthRepository: AuthRepository) : ViewModel() {
    private val _loginState = MutableStateFlow<LogInUiState>(LogInUiState.Idle)
    val loginUiState: StateFlow<LogInUiState> = _loginState.asStateFlow()

    fun resetState() {
        _loginState.value = LogInUiState.Idle
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

    fun login(user: LoginDto) {
        viewModelScope.launch {
            _loginState.value = LogInUiState.Loading
            try {
                val response = networkAuthRepository.login(user)
                _loginState.value = LogInUiState.Success(response.token)
            } catch (_: IOException) {
                _loginState.value = LogInUiState.Error
            } catch (_: HttpException) {
                _loginState.value = LogInUiState.Error
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as AuthApplication)
                val authRepository = application.container.authRepository
                LogInViewModel(networkAuthRepository = authRepository)
            }
        }
    }
}

sealed interface LogInUiState {
    data class Success(val token: String) : LogInUiState
    object Error : LogInUiState
    object Loading : LogInUiState
    object Idle : LogInUiState
}
