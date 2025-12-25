package com.example.academy_tbc.presentation.screen.auth.sign_in

import androidx.lifecycle.viewModelScope
import com.example.academy_tbc.domain.model.auth.sign_in.SignInValidationError
import com.example.academy_tbc.domain.preferences.AppPreferencesKeys
import com.example.academy_tbc.domain.usecase.auth.sign_in.SignInUseCase
import com.example.academy_tbc.domain.usecase.auth.sign_in.validation.ValidateEmailUseCase
import com.example.academy_tbc.domain.usecase.auth.sign_in.validation.ValidatePasswordUseCase
import com.example.academy_tbc.domain.usecase.datastore.SetPreferenceUseCase
import com.example.academy_tbc.presentation.common.mapper.toGenericString
import com.example.academy_tbc.presentation.common.view.BaseViewModel
import com.example.academy_tbc.presentation.screen.auth.sign_in.mapper.toGenericString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
    private val setPreferenceUseCase: SetPreferenceUseCase,
    private val signInUseCase: SignInUseCase,
) : BaseViewModel<SignInState, SignInSideEffect, SignInEvent>(SignInState()) {


    override fun onEvent(event: SignInEvent) {
        when (event) {
            is SignInEvent.SignIn -> signIn(
                email = event.email,
                password = event.password,
                rememberMe = event.rememberMe
            )
        }

    }

    private fun signIn(email: String, password: String, rememberMe: Boolean) {
        if (validateInputs(email = email, password = password)) {
            launchResource(
                apiCall = signInUseCase(email = email, password = password),
                onLoading = { loading ->
                    updateUiState { copy(isLoading = loading) }
                },
                onSuccess = { signInResult ->
                    viewModelScope.launch {
                        if (rememberMe) {
                            val token = signInResult.token
                            val fullName = signInResult.fullName
                            val phoneNumber = signInResult.phoneNumber
                            val email = signInResult.email
                            val department = signInResult.department
                            setPreferenceUseCase.invoke(AppPreferencesKeys.TOKEN, token)
                            setPreferenceUseCase.invoke(AppPreferencesKeys.FULL_NAME, fullName)
                            setPreferenceUseCase.invoke(
                                AppPreferencesKeys.PHONE_NUMBER,
                                phoneNumber
                            )
                            setPreferenceUseCase.invoke(AppPreferencesKeys.EMAIL, email)
                            setPreferenceUseCase.invoke(AppPreferencesKeys.DEPARTMENT, department)
                        }
                        emitSideEffect(SignInSideEffect.NavigateToHome)
                    }

                },
                onError = {
                    emitSideEffect(SignInSideEffect.ShowError(error = it.toGenericString()))
                }
            )
        }
    }

    private fun validateInputs(email: String, password: String): Boolean {
        val isEmailValid = when (val emailError = validateEmailUseCase(email)) {
            SignInValidationError.EMAIL_EMPTY -> {
                emitSideEffect(SignInSideEffect.ShowEmailError(emailError.toGenericString()))
                false
            }

            SignInValidationError.EMAIL_WRONG_FORMAT -> {
                emitSideEffect(SignInSideEffect.ShowEmailError(emailError.toGenericString()))
                false
            }

            SignInValidationError.VALID -> true

            else -> true
        }
        val isPasswordValid = when (val passwordError = validatePasswordUseCase(password)) {
            SignInValidationError.PASSWORD_EMPTY -> {
                emitSideEffect(SignInSideEffect.ShowPasswordError(passwordError.toGenericString()))
                false
            }

            SignInValidationError.VALID -> true

            else -> true
        }
        return isEmailValid && isPasswordValid
    }
}