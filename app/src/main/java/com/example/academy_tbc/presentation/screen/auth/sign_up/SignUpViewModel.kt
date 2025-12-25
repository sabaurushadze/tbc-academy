package com.example.academy_tbc.presentation.screen.auth.sign_up

import android.os.SystemClock
import androidx.lifecycle.viewModelScope
import com.example.academy_tbc.domain.model.auth.sign_up.SignUpValidationError
import com.example.academy_tbc.domain.usecase.auth.department.GetDepartmentsUseCase
import com.example.academy_tbc.domain.usecase.auth.sign_up.SignUpUseCase
import com.example.academy_tbc.domain.usecase.auth.sign_up.validation.ValidateFirstNameUseCase
import com.example.academy_tbc.domain.usecase.auth.sign_up.validation.ValidateLastNameUseCase
import com.example.academy_tbc.domain.usecase.auth.sign_up.validation.ValidateOtpCodeUseCase
import com.example.academy_tbc.domain.usecase.auth.sign_up.validation.ValidatePhoneNumberUseCase
import com.example.academy_tbc.domain.usecase.auth.sign_up.validation.ValidateSignUpConfirmPasswordUseCase
import com.example.academy_tbc.domain.usecase.auth.sign_up.validation.ValidateSignUpEmailUseCase
import com.example.academy_tbc.domain.usecase.auth.sign_up.validation.ValidateSignUpPasswordUseCase
import com.example.academy_tbc.presentation.common.mapper.toGenericString
import com.example.academy_tbc.presentation.common.view.BaseViewModel
import com.example.academy_tbc.presentation.screen.auth.sign_up.mapper.toGenericString
import com.example.academy_tbc.presentation.screen.auth.sign_up.mapper.toPresentation
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

    private val signUpUseCase: SignUpUseCase,
    private val getDepartmentsUseCase: GetDepartmentsUseCase,
) : BaseViewModel<SignUpState, SignUpSideEffect, SignUpEvent>(SignUpState()) {

    init {
        getDepartments()
    }

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
                confirmPassword = event.confirmPassword,
                department = event.department,
                phoneNumber = event.phoneNumber
            )

            is SignUpEvent.ValidateCode -> validateOtpCodeOnServer(event.otpCode)
            is SignUpEvent.SaveDepartment -> saveDepartment(event.department)
        }
    }

    private fun saveDepartment(departmentId: Int) {
        updateUiState { copy(selectedDepartment = departmentId) }
    }

    private fun getDepartments() {
        launchResource(
            apiCall = getDepartmentsUseCase(),
            onLoading = {
                updateUiState { copy(isLoading = isLoading) }
            },
            onSuccess = {
                updateUiState { copy(departments = it.map { it.toPresentation() }) }
            },
            onError = {
                emitSideEffect(SignUpSideEffect.ShowError(error = it.toGenericString()))
            }
        )
    }

    private fun validateOtpCodeOnServer(otpCode: String) {
        if (validateOtpCode(otpCode)) {
//            SERVER CHECK HERE, IF ITS SUCCESS THERE SEND SUCCESS EFFECT,
//            da timeri daacencele
            otpTimerJob?.cancel()
            emitSideEffect(SignUpSideEffect.OtpCodeValid)
        }
    }

    private fun validateOtpCode(otpCode: String): Boolean {
        val otpCodeOk = handleValidation(validateOtpCodeUseCase(otpCode)) {
            emitSideEffect(SignUpSideEffect.ShowOtpCodeError(it))
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
        confirmPassword: String,
    ): Boolean {

        val firstOk = handleValidation(validateFirstNameUseCase(firstName)) {
            emitSideEffect(SignUpSideEffect.ShowFirstNameError(it))
        }

        val lastOk = handleValidation(validateLastNameUseCase(lastName)) {
            emitSideEffect(SignUpSideEffect.ShowLastNameError(it))
        }

        val emailOk = handleValidation(validateEmailUseCase(email)) {
            emitSideEffect(SignUpSideEffect.ShowEmailError(it))
        }

        val passOk = handleValidation(validatePasswordUseCase(password)) {
            emitSideEffect(SignUpSideEffect.ShowPasswordError(it))
        }

        val confirmPassOk = handleValidation(
            validateSignUpConfirmPasswordUseCase(password, confirmPassword)
        ) {
            emitSideEffect(SignUpSideEffect.ShowConfirmPasswordError(it))
        }


        return firstOk && lastOk && emailOk && passOk && confirmPassOk
    }


    private fun validatePhoneNumber(phoneNumber: String): Boolean {
        val phoneOk = handleValidation(validatePhoneNumberUseCase(phoneNumber)) {
            emitSideEffect(SignUpSideEffect.ShowPhoneNumberError(it))
        }

        return phoneOk
    }


    private fun signUp(
        firstName: String,
        lastName: String,
        email: String,
        password: String,
        confirmPassword: String,
        department: Int,
        phoneNumber: String,
    ) {
        if (validateInputs(
                firstName = firstName,
                lastName = lastName,
                email = email,
                password = password,
                confirmPassword = confirmPassword

            )
        ) {
            launchResource(
                apiCall = signUpUseCase(
                    firstName = firstName,
                    lastName = lastName,
                    email = email,
                    password = password,
                    department = department,
                    phoneNumber = phoneNumber
                ),
                onLoading = {
                    updateUiState { copy(isLoading = isLoading) }
                },
                onSuccess = {
                    emitSideEffect(SignUpSideEffect.SuccessAndNavigateToSignIn)
                },
                onError = {
                    emitSideEffect(SignUpSideEffect.ShowError(error = it.toGenericString()))
                }
            )
        }
    }

    private fun startOtpTimer(phoneNumber: String) {
        if (!validatePhoneNumber(phoneNumber)) return
        otpTimerJob?.cancel()

        otpStartTime = SystemClock.elapsedRealtime()

        updateUiState { copy(isOtpVisible = true, elapsedTime = otpDuration) }

        otpTimerJob = viewModelScope.launch {
            var remainingTime = otpDuration
            while (remainingTime > 0) {
                updateUiState { copy(elapsedTime = remainingTime) }
                delay(1000)
                remainingTime -= 1000L
            }
            updateUiState { copy(elapsedTime = 0L, isOtpVisible = false) }
            emitSideEffect(SignUpSideEffect.OtpExpired)
        }
    }

    override fun onCleared() {
        super.onCleared()
        otpTimerJob?.cancel()
    }


}