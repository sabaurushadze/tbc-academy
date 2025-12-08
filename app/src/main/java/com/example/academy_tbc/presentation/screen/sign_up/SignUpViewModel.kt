package com.example.academy_tbc.presentation.screen.sign_up

import androidx.lifecycle.viewModelScope
import com.example.academy_tbc.domain.repository.auth.AuthRepository
import com.example.academy_tbc.domain.resource.Resource
import com.example.academy_tbc.presentation.common.view.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authRepository: AuthRepository,
) : BaseViewModel<SignUpState, SignUpSideEffect, SignUpEvent>(SignUpState()) {
    override fun onEvent(event: SignUpEvent) {
        when (event) {
            is SignUpEvent.EmailChanged -> updateState { copy(email = event.value) }
            is SignUpEvent.PasswordChanged -> updateState { copy(password = event.value) }
            is SignUpEvent.SignUpWithEmailAndPassword ->
                signUpWithEmailAndPassword(email = event.email, password = event.password)
        }
    }

    private fun signUpWithEmailAndPassword(email: String, password: String) {
        viewModelScope.launch {
            authRepository.signUp(email = email, password = password).collect { resource ->
                when (resource) {
                    is Resource.Loading -> updateState { copy(isLoading = resource.isLoading) }
                    is Resource.Success -> sendEffect(SignUpSideEffect.NavigateToHome)
                    is Resource.Error -> sendEffect(
                        SignUpSideEffect.ShowError(
                            resource.error
                        )
                    )
                }
            }
        }
    }
}
