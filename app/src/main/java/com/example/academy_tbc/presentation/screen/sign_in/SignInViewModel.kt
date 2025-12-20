package com.example.academy_tbc.presentation.screen.sign_in

import com.example.academy_tbc.presentation.common.view.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
) : BaseViewModel<SignInState, SignInSideEffect, SignInEvent>(SignInState()) {


    override fun onEvent(event: SignInEvent) {

    }
}