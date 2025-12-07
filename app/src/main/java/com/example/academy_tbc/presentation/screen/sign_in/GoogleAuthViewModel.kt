package com.example.academy_tbc.presentation.screen.sign_in

import androidx.lifecycle.viewModelScope
import com.example.academy_tbc.presentation.common.view.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GoogleAuthViewModel @Inject constructor(
    private val authUiClient: GoogleAuthUiClient
) : BaseViewModel<SignInState, Unit, Unit>(SignInState()) {

    fun signIn() = viewModelScope.launch {
        val result = authUiClient.signIn()
        onSignInResult(result)
    }

    fun onSignInResult(result: SignInResult) {
        updateState {
            copy(
                isSignInSuccessful = result.data != null,
                user = result.data,
            )
        }
    }

    fun resetState() {
        updateState { SignInState() }
    }

    fun signOut() = viewModelScope.launch {
        authUiClient.signOut()
        updateState { SignInState() }
    }
}