package com.example.academy_tbc.presentation.screen.sign_up

import android.os.SystemClock
import androidx.lifecycle.viewModelScope
import com.example.academy_tbc.presentation.common.view.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
) : BaseViewModel<SignUpState, SignUpSideEffect, SignUpEvent>(SignUpState()) {

    private var otpTimerJob: Job? = null
    private var otpStartTime = 0L
    private val otpDuration = 5_000L

    override fun onEvent(event: SignUpEvent) {
        when (event) {
            SignUpEvent.ResendOtpClicked,
            SignUpEvent.SendOtpClicked,
                -> startOtpTimer()
        }
    }

    private fun startOtpTimer() {
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