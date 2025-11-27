package com.example.academy_tbc.presentation.screen.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.academy_tbc.data.common.Resource
import com.example.academy_tbc.data.remote.register.repository.RegisterRepository
import com.example.academy_tbc.presentation.screen.register.RegisterSideEffect.NavigateToLogin
import com.example.academy_tbc.presentation.screen.register.RegisterSideEffect.ShowError
import com.example.academy_tbc.presentation.screen.register.RegisterSideEffect.ShowServerError
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
class RegisterViewModel @Inject constructor(
    private val registerRepository: RegisterRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(RegisterState())
    val state: StateFlow<RegisterState> = _state.asStateFlow()

    private val _sideEffect = MutableSharedFlow<RegisterSideEffect>()
    val sideEffect = _sideEffect.asSharedFlow()

    fun onEvent(event: RegisterEvent) {
        when (event) {
            is RegisterEvent.Register -> register(
                email = event.email, password = event.password
            )

            is RegisterEvent.EmailChanged -> updateEmail(event.email)
            is RegisterEvent.PasswordChanged -> updatePassword(event.password)
            is RegisterEvent.RepeatPasswordChanged -> updateRepeatPassword(event.repeatPassword)
        }
    }

    private fun updateEmail(email: String) {
        _state.update { current ->
            val isRegisterEnabled = validateInputs(
                email = email, password = current.password
            ) && current.password == current.repeatPassword
            current.copy(email = email, isRegisterEnabled = isRegisterEnabled)
        }
    }

    private fun updatePassword(password: String) {
        _state.update { current ->
            val isRegisterEnabled = validateInputs(
                email = current.email, password = password
            ) && password == current.repeatPassword
            current.copy(password = password, isRegisterEnabled = isRegisterEnabled)
        }
    }

    private fun updateRepeatPassword(repeatPassword: String) {
        _state.update { current ->
            val isRegisterEnabled = validateInputs(
                email = current.email, password = current.password
            ) && current.password == repeatPassword
            current.copy(repeatPassword = repeatPassword, isRegisterEnabled = isRegisterEnabled)
        }
    }

    private fun validateInputs(email: String, password: String) =
        RegisterValidations.validateEmail(email) && RegisterValidations.validatePassword(password)

    fun register(email: String, password: String) {
        viewModelScope.launch {
            registerRepository.register(email = email, password = password).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        _sideEffect.emit(
                            NavigateToLogin(
                                email = email, password = password
                            )
                        )
                    }

                    is Resource.Error -> _sideEffect.emit(
                        ShowError(
                            result.errorRes
                        )
                    )

                    is Resource.ServerError -> _sideEffect.emit(
                        ShowServerError(
                            result.errorCode
                        )
                    )

                    is Resource.Loading -> _state.update { it.copy(isLoading = result.isLoading) }
                }
            }
        }
    }
}
