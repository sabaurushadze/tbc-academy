package com.example.academy_tbc.presentation.screen.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.academy_tbc.data.common.Resource
import com.example.academy_tbc.data.local.preferences.PreferenceKeys
import com.example.academy_tbc.data.local.repository.DataStoreRepository
import com.example.academy_tbc.data.remote.login.repository.LogInRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class LogInViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository,
    private val logInRepository: LogInRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(LogInState())
    val state: StateFlow<LogInState> = _state.asStateFlow()

    private val _sideEffect = MutableSharedFlow<LogInSideEffect>()
    val sideEffect = _sideEffect.asSharedFlow()

    fun onEvent(event: LogInEvent) {
        when (event) {
            is LogInEvent.LogIn -> logIn(
                email = event.email, password = event.password, isRemembered = event.isRemembered
            )

            is LogInEvent.EmailChanged -> updateEmail(event.email)
            is LogInEvent.PasswordChanged -> updatePassword(event.password)
            is LogInEvent.RememberMeChanged -> updateRememberMe(event.isRemembered)
        }
    }

    private fun updateEmail(email: String) {
        _state.update { current ->
            val isLoginEnabled = validateInputs(email = email, password = current.password)
            current.copy(email = email, isLoginEnabled = isLoginEnabled)
        }
    }

    private fun updatePassword(password: String) {
        _state.update { current ->
            val isLoginEnabled = validateInputs(email = current.email, password = password)
            current.copy(password = password, isLoginEnabled = isLoginEnabled)
        }
    }

    private fun updateRememberMe(isRemembered: Boolean) {
        _state.update { it.copy(isRemembered = isRemembered) }
    }

    private fun validateInputs(email: String, password: String) =
        LogInValidations.validateEmail(email) && LogInValidations.validatePassword(password)

    private fun logIn(
        email: String, password: String, isRemembered: Boolean,
    ) {
        viewModelScope.launch {
            logInRepository.logIn(email = email, password = password).collect { result ->
                when (result) {
                    is Resource.Loading -> _state.update { it.copy(isLoading = result.isLoading) }
                    is Resource.Success -> {
                        if (isRemembered) {
                            dataStoreRepository.putPreference(
                                value = result.data.token,
                                key = PreferenceKeys.USER_TOKEN
                            )
                        }
                        dataStoreRepository.putPreference(PreferenceKeys.USER_EMAIL, email)

                        _sideEffect.emit(LogInSideEffect.NavigateToHome)
                    }

                    is Resource.Error -> _sideEffect.emit(
                        LogInSideEffect.ShowError(
                            result.errorRes
                        )
                    )
                    is Resource.ServerError -> _sideEffect.emit(
                        LogInSideEffect.ShowServerError(
                            result.errorCode
                        )
                    )
                }
            }
        }
    }
}
