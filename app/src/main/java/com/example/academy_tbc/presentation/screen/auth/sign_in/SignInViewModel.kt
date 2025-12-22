package com.example.academy_tbc.presentation.screen.auth.sign_in

import com.example.academy_tbc.domain.model.auth.sign_in.SignInValidationError
import com.example.academy_tbc.domain.usecase.auth.sign_in.ValidateEmailUseCase
import com.example.academy_tbc.domain.usecase.auth.sign_in.ValidatePasswordUseCase
import com.example.academy_tbc.presentation.common.view.BaseViewModel
import com.example.academy_tbc.presentation.screen.auth.sign_in.mapper.toGenericString
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
) : BaseViewModel<SignInState, SignInSideEffect, SignInEvent>(SignInState()) {


    override fun onEvent(event: SignInEvent) {
        when(event) {
            is SignInEvent.SignIn -> signIn(
                email = event.email,
                password = event.password,
                rememberMe = event.rememberMe
            )
        }

    }

    private fun signIn(email: String, password: String, rememberMe: Boolean) {
        if (validateInputs(email = email, password = password)) {
//            proceed sign in
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