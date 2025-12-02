package com.example.academy_tbc.presentation.screen.security

import com.example.academy_tbc.presentation.common.view.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SecurityViewModel @Inject constructor() :
    BaseViewModel<SecurityState, SecuritySideEffect, SecurityEvent>(SecurityState()) {
    override fun onEvent(event: SecurityEvent) {
        when (event) {
            is SecurityEvent.NumberPressed -> onDigitEntered(event.value)
            SecurityEvent.Backspace -> onBackspacePressed()
            SecurityEvent.BiometricClick -> biometricAuthentication()
        }
    }

    private fun onBackspacePressed() {
        updateState {
            copy(enteredCode = enteredCode.dropLast(1))
        }
    }

    private fun onDigitEntered(digit: String) {
        if (state.value.enteredCode.length < PIN_LENGTH) {
            val newCode = state.value.enteredCode + digit

            updateState { copy(enteredCode = newCode) }

            if (newCode.length == PIN_LENGTH) {
                checkCode(newCode)
            }
        }
    }

    private fun checkCode(code: String) {
        if (code == CORRECT_PASSCODE) {
            sendEffect(SecuritySideEffect.Success)
            updateState { copy(enteredCode = "") }
        } else {
            sendEffect(SecuritySideEffect.WrongCode)
            updateState { copy(enteredCode = "") }
        }
    }

    private fun biometricAuthentication() {
        updateState { copy(enteredCode = "") }
        sendEffect(SecuritySideEffect.LaunchBiometric)
    }


    companion object {
        private const val CORRECT_PASSCODE = "0934"
        private const val PIN_LENGTH = 4
    }

}