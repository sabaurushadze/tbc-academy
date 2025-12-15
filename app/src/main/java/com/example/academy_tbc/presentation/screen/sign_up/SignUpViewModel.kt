package com.example.academy_tbc.presentation.screen.sign_up

import androidx.lifecycle.viewModelScope
import com.example.academy_tbc.domain.common.Resource
import com.example.academy_tbc.domain.common.isLoading
import com.example.academy_tbc.domain.model.auth.AuthValidationError
import com.example.academy_tbc.domain.usecase.auth.SignUpWithEmailAndPasswordUseCase
import com.example.academy_tbc.domain.usecase.auth.ValidateEmailUseCase
import com.example.academy_tbc.domain.usecase.auth.ValidatePasswordUseCase
import com.example.academy_tbc.presentation.common.view.BaseViewModel
import com.example.academy_tbc.presentation.screen.sign_in.mapper.toGenericString
import com.example.academy_tbc.presentation.screen.sign_up.mapper.toGenericString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpWithEmailAndPassword: SignUpWithEmailAndPasswordUseCase,
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
) : BaseViewModel<SignUpState, SignUpSideEffect, SignUpEvent>(SignUpState()) {
    override fun onEvent(event: SignUpEvent) {
        when (event) {
            is SignUpEvent.EmailChanged -> updateState { copy(email = event.value) }
            is SignUpEvent.PasswordChanged -> updateState { copy(password = event.value) }
            is SignUpEvent.SignUpWithEmailAndPassword ->
                onSignUpWithEmailAndPassword(email = event.email, password = event.password)
        }
    }

    private fun validateEmail(email: String): Boolean {
        val error = validateEmailUseCase(email)

        return if (error != AuthValidationError.UNKNOWN) {
            sendEffect(
                SignUpSideEffect.ShowError(
                    error.toGenericString()
                )
            )
            false
        } else {
            true
        }
    }

    private fun validatePassword(password: String): Boolean {
        val error = validatePasswordUseCase(password)

        return if (error != AuthValidationError.UNKNOWN) {
            sendEffect(
                SignUpSideEffect.ShowError(
                    error.toGenericString()
                )
            )
            false
        } else {
            true
        }
    }

    private fun onSignUpWithEmailAndPassword(email: String, password: String) {
        viewModelScope.launch {
            if (!validateEmail(email)) return@launch
            if (!validatePassword(password)) return@launch

            signUpWithEmailAndPassword(email = email, password = password).collect { resource ->
                when (resource) {
                    is Resource.Loading -> updateState { copy(isLoading = resource.isLoading()) }
                    is Resource.Success -> {
                        sendEffect(SignUpSideEffect.NavigateToHome)
                        updateState { copy(isLoading = false) }
                    }

                    is Resource.Error -> {
                        sendEffect(
                            SignUpSideEffect.ShowError(
                                resource.error.toGenericString()
                            )
                        )
                        updateState { copy(isLoading = false) }
                    }
                }
            }
        }
    }
}
