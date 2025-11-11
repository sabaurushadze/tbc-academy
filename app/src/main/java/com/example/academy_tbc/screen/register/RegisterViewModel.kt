package com.example.academy_tbc.screen.register

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

class RegisterViewModel(private val networkAuthRepository: AuthRepository) : ViewModel() {
    private val _registerState = MutableStateFlow<RegisterUiState>(RegisterUiState.Idle)
    val registerUiState: StateFlow<RegisterUiState> = _registerState.asStateFlow()

    fun resetState() {
        _registerState.value = RegisterUiState.Idle
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

    fun register(user: RegisterDto) {
        viewModelScope.launch {
            _registerState.value = RegisterUiState.Loading
            try {
                val result = networkAuthRepository.register(user)
                _registerState.value = RegisterUiState.Success(result.token)
            } catch (_: IOException) {
                _registerState.value = RegisterUiState.Error
            } catch (_: HttpException) {
                _registerState.value = RegisterUiState.Error
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as AuthApplication)
                val authRepository = application.container.authRepository
                RegisterViewModel(networkAuthRepository = authRepository)
            }
        }
    }
}

sealed interface RegisterUiState {
    data class Success(val token: String) : RegisterUiState
    object Error : RegisterUiState
    object Loading : RegisterUiState
    object Idle : RegisterUiState
}