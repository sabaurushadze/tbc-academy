package com.example.academy_tbc.presentation.screen.sign_in

import androidx.lifecycle.viewModelScope
import com.example.academy_tbc.domain.repository.auth.AuthRepository
import com.example.academy_tbc.domain.resource.Resource
import com.example.academy_tbc.presentation.common.view.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val authRepository: AuthRepository,
) : BaseViewModel<SignInState, SignInSideEffect, SignInEvent>(SignInState()) {
    override fun onEvent(event: SignInEvent) {
        when (event) {
            is SignInEvent.SignInWithEmailAndPassword ->
                signInWithEmailAndPassword(email = event.email, password = event.password)

            is SignInEvent.SignInWithGoogle -> onSignInWithGoogle(event.idToken)
        }
    }

    private fun signInWithEmailAndPassword(email: String, password: String) {
        viewModelScope.launch {
            authRepository.signIn(email = email, password = password).collect { resource ->
                when (resource) {
                    is Resource.Loading -> updateState { copy(isLoading = resource.isLoading) }
                    is Resource.Success -> sendEffect(SignInSideEffect.NavigateToHome)
                    is Resource.Error -> sendEffect(
                        SignInSideEffect.ShowError(
                            resource.error
                        )
                    )
                }
            }
        }
    }

    private fun onSignInWithGoogle(idToken: String) {
        viewModelScope.launch {
            authRepository.signInWithGoogle(idToken).collect { resource ->
                when (resource) {
                    is Resource.Loading -> updateState { copy(isLoading = resource.isLoading) }
                    is Resource.Success -> sendEffect(SignInSideEffect.NavigateToHome)
                    is Resource.Error -> sendEffect(
                        SignInSideEffect.ShowError(
                            resource.error
                        )
                    )
                }
            }
        }
    }
}
