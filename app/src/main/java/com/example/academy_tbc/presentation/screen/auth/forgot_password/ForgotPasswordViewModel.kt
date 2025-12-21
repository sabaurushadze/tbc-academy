package com.example.academy_tbc.presentation.screen.auth.forgot_password

import com.example.academy_tbc.presentation.common.view.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
) : BaseViewModel<ForgotPasswordState, ForgotPasswordSideEffect, ForgotPasswordEvent>(ForgotPasswordState()) {


    override fun onEvent(event: ForgotPasswordEvent) {
    }


}