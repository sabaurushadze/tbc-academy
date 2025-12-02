package com.example.academy_tbc.presentation.screen.register

import androidx.lifecycle.viewModelScope
import com.example.academy_tbc.domain.resource.Resource
import com.example.academy_tbc.domain.usecase.register.RegisterUseCase
import com.example.academy_tbc.domain.usecase.validations.EmailValidationUseCase
import com.example.academy_tbc.domain.usecase.validations.PasswordValidationUseCase
import com.example.academy_tbc.presentation.common.mapper.toMessage
import com.example.academy_tbc.presentation.common.view.BaseViewModel
import com.example.academy_tbc.presentation.screen.register.RegisterSideEffect.NavigateToLogin
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase,
    private val emailValidationUseCase: EmailValidationUseCase,
    private val passwordValidationUseCase: PasswordValidationUseCase,
) : BaseViewModel<RegisterState, RegisterSideEffect, RegisterEvent>(RegisterState()) {

    override fun onEvent(event: RegisterEvent) {
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
        updateState {
            val enabled = validateInputs(
                email = email, password = password
            ) && password == repeatPassword
            copy(email = email, isRegisterEnabled = enabled)
        }
    }

    private fun updatePassword(password: String) {
        updateState {
            val enabled = validateInputs(
                email = email, password = password
            ) && password == repeatPassword
            copy(password = password, isRegisterEnabled = enabled)
        }
    }

    private fun updateRepeatPassword(repeatPassword: String) {
        updateState {
            val enabled = validateInputs(
                email = email, password = password
            ) && password == repeatPassword
            copy(repeatPassword = repeatPassword, isRegisterEnabled = enabled)
        }
    }

    private fun validateInputs(email: String, password: String) =
        emailValidationUseCase(email) && passwordValidationUseCase(password)

    fun register(email: String, password: String) {
        viewModelScope.launch {
            registerUseCase(email = email, password = password).collect { result ->
                when (result) {
                    is Resource.Success -> sendEffect(
                        NavigateToLogin(
                            email = email,
                            password = password
                        )
                    )

                    is Resource.Loading -> updateState { copy(isLoading = result.isLoading) }
                    is Resource.Error -> sendEffect(RegisterSideEffect.ShowError(result.error.toMessage()))
                }
            }
        }
    }
}