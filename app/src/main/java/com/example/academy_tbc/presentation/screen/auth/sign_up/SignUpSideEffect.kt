package com.example.academy_tbc.presentation.screen.auth.sign_up

import com.example.academy_tbc.presentation.util.GenericString

sealed interface SignUpSideEffect {
//    errors
    data class ShowError(val error: GenericString) : SignUpSideEffect
    data class ShowEmailError(val error: GenericString) : SignUpSideEffect
    data class ShowPasswordError(val error: GenericString) : SignUpSideEffect
    data class ShowConfirmPasswordError(val error: GenericString) : SignUpSideEffect
    data class ShowFirstNameError(val error: GenericString) : SignUpSideEffect
    data class ShowLastNameError(val error: GenericString) : SignUpSideEffect
    data class ShowPhoneNumberError(val error: GenericString) : SignUpSideEffect
    data class ShowOtpCodeError(val error: GenericString) : SignUpSideEffect
    data object OtpExpired : SignUpSideEffect

//    success
    data object OtpCodeValid : SignUpSideEffect
    data object SuccessAndNavigateToSignIn : SignUpSideEffect




}