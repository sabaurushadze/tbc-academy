package com.example.academy_tbc.presentation.screen.sign_in

import androidx.lifecycle.viewModelScope
import com.example.academy_tbc.domain.common.Resource
import com.example.academy_tbc.domain.common.isLoading
import com.example.academy_tbc.domain.usecase.auth.SignInWithEmailAndPasswordUseCase
import com.example.academy_tbc.domain.usecase.auth.SignInWithGoogleUseCase
import com.example.academy_tbc.presentation.common.view.BaseViewModel
import com.example.academy_tbc.presentation.screen.sign_in.mapper.toGenericString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signInWithGoogle: SignInWithGoogleUseCase,
    private val signInWithEmailAndPassword: SignInWithEmailAndPasswordUseCase,
) : BaseViewModel<SignInState, SignInSideEffect, SignInEvent>(SignInState()) {
    override fun onEvent(event: SignInEvent) {
        when (event) {
            is SignInEvent.SignInWithEmailAndPassword ->
                onSignInWithEmailAndPassword(email = event.email, password = event.password)

            is SignInEvent.SignInWithGoogle -> onSignInWithGoogle(event.idToken)
        }
    }

    private fun onSignInWithEmailAndPassword(email: String, password: String) {
        viewModelScope.launch {
            signInWithEmailAndPassword(email = email, password = password).collect { resource ->
                when (resource) {
                    is Resource.Loading -> updateState { copy(isLoading = resource.isLoading()) }
                    is Resource.Success -> sendEffect(SignInSideEffect.NavigateToHome)
                    is Resource.Error -> sendEffect(
                        SignInSideEffect.ShowError(
                            resource.error.toGenericString()
                        )
                    )
                }
            }
        }
    }

    private fun onSignInWithGoogle(idToken: String) {
        viewModelScope.launch {
            signInWithGoogle(idToken).collect { resource ->
                when (resource) {
                    is Resource.Loading -> updateState { copy(isLoading = resource.isLoading()) }
                    is Resource.Success -> sendEffect(SignInSideEffect.NavigateToHome)
                    is Resource.Error -> sendEffect(
                        SignInSideEffect.ShowError(
                            resource.error.toGenericString()
                        )
                    )
                }
            }
        }
    }
}
