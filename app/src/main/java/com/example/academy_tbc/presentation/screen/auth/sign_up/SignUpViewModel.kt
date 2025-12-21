package com.example.academy_tbc.presentation.screen.auth.sign_up

import android.os.SystemClock
import androidx.lifecycle.viewModelScope
import com.example.academy_tbc.domain.model.auth.sign_up.SignUpValidationError
import com.example.academy_tbc.domain.usecase.auth.sign_up.ValidateFirstNameUseCase
import com.example.academy_tbc.domain.usecase.auth.sign_up.ValidateLastNameUseCase
import com.example.academy_tbc.domain.usecase.auth.sign_up.ValidateOtpCodeUseCase
import com.example.academy_tbc.domain.usecase.auth.sign_up.ValidatePhoneNumberUseCase
import com.example.academy_tbc.domain.usecase.auth.sign_up.ValidateSignUpConfirmPasswordUseCase
import com.example.academy_tbc.domain.usecase.auth.sign_up.ValidateSignUpEmailUseCase
import com.example.academy_tbc.domain.usecase.auth.sign_up.ValidateSignUpPasswordUseCase
import com.example.academy_tbc.presentation.common.view.BaseViewModel
import com.example.academy_tbc.presentation.screen.auth.sign_up.mapper.toGenericString
import com.example.academy_tbc.presentation.util.GenericString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val validateFirstNameUseCase: ValidateFirstNameUseCase,
    private val validateLastNameUseCase: ValidateLastNameUseCase,
    private val validateEmailUseCase: ValidateSignUpEmailUseCase,
    private val validatePhoneNumberUseCase: ValidatePhoneNumberUseCase,
    private val validateOtpCodeUseCase: ValidateOtpCodeUseCase,
    private val validatePasswordUseCase: ValidateSignUpPasswordUseCase,
    private val validateSignUpConfirmPasswordUseCase: ValidateSignUpConfirmPasswordUseCase,


    ) : BaseViewModel<SignUpState, SignUpSideEffect, SignUpEvent>(SignUpState()) {

    private var otpTimerJob: Job? = null
    private var otpStartTime = 0L
    private val otpDuration = 60_000L

    override fun onEvent(event: SignUpEvent) {
        when (event) {
            is SignUpEvent.ResendOtp -> startOtpTimer(event.phoneNumber)
            is SignUpEvent.SendOtp -> startOtpTimer(event.phoneNumber)

            is SignUpEvent.SignUp -> signUp(
                firstName = event.firstName,
                lastName = event.lastName,
                email = event.email,
                password = event.password,
                confirmPassword = event.confirmPassword
            )

            is SignUpEvent.ValidateCode -> validateOtpCodeOnServer(event.otpCode)
        }
    }


    private fun validateOtpCodeOnServer(otpCode: String) {
        if (validateOtpCode(otpCode)) {
//            SERVER CHECK HERE, IF ITS SUCCESS THERE SEND SUCCESS EFFECT,
//            da timeri daacencele
            otpTimerJob?.cancel()
            sendEffect(SignUpSideEffect.OtpCodeValid)
        }
    }

    private fun validateOtpCode(otpCode: String): Boolean {
        val otpCodeOk = handleValidation(validateOtpCodeUseCase(otpCode)) {
            sendEffect(SignUpSideEffect.ShowOtpCodeError(it))
        }

        return otpCodeOk
    }

    private fun handleValidation(
        error: SignUpValidationError,
        onError: (GenericString) -> Unit,
    ): Boolean {
        return if (error == SignUpValidationError.VALID) {
            true
        } else {
            onError(error.toGenericString())
            false
        }
    }

    private fun validateInputs(
        firstName: String,
        lastName: String,
        email: String,
        password: String,
        confirmPassword: String
    ): Boolean {

        val firstOk = handleValidation(validateFirstNameUseCase(firstName)) {
            sendEffect(SignUpSideEffect.ShowFirstNameError(it))
        }

        val lastOk = handleValidation(validateLastNameUseCase(lastName)) {
            sendEffect(SignUpSideEffect.ShowLastNameError(it))
        }

        val emailOk = handleValidation(validateEmailUseCase(email)) {
            sendEffect(SignUpSideEffect.ShowEmailError(it))
        }

        val passOk = handleValidation(validatePasswordUseCase(password)) {
            sendEffect(SignUpSideEffect.ShowPasswordError(it))
        }

//        val confirmPassOk = handleValidation(validatePasswordUseCase(confirmPassword)) {
//            sendEffect(SignUpSideEffect.ShowPasswordError(it))
//        }

        val confirmPassOk = handleValidation(
            validateSignUpConfirmPasswordUseCase(password, confirmPassword)
        ) {
            sendEffect(SignUpSideEffect.ShowConfirmPasswordError(it))
        }


        return firstOk && lastOk && emailOk && passOk && confirmPassOk
    }


    private fun validatePhoneNumber(phoneNumber: String): Boolean {
        val phoneOk = handleValidation(validatePhoneNumberUseCase(phoneNumber)) {
            sendEffect(SignUpSideEffect.ShowPhoneNumberError(it))
        }

        return phoneOk
    }


    private fun signUp(
        firstName: String,
        lastName: String,
        email: String,
        password: String,
        confirmPassword: String
    ) {
        if (validateInputs(
                firstName = firstName,
                lastName = lastName,
                email = email,
                password = password,
                confirmPassword = confirmPassword

            )) {
//            create account
        }
    }

    private fun startOtpTimer(phoneNumber: String) {
        if (!validatePhoneNumber(phoneNumber)) return
        otpTimerJob?.cancel()

        otpStartTime = SystemClock.elapsedRealtime()

        updateState { copy(isOtpVisible = true, elapsedTime = otpDuration) }

        otpTimerJob = viewModelScope.launch {
            var remainingTime = otpDuration
            while (remainingTime > 0) {
                updateState { copy(elapsedTime = remainingTime) }
                delay(1000)
                remainingTime -= 1000L
            }
            updateState { copy(elapsedTime = 0L, isOtpVisible = false) }
            sendEffect(SignUpSideEffect.OtpExpired)
        }
    }

    override fun onCleared() {
        super.onCleared()
        otpTimerJob?.cancel()
    }


}