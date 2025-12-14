package com.example.academy_tbc.presentation.screen.sign_up

import androidx.lifecycle.viewModelScope
import com.example.academy_tbc.domain.common.Resource
import com.example.academy_tbc.domain.common.isLoading
import com.example.academy_tbc.domain.usecase.auth.SignUpWithEmailAndPasswordUseCase
import com.example.academy_tbc.presentation.common.view.BaseViewModel
import com.example.academy_tbc.presentation.screen.sign_in.mapper.toGenericString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpWithEmailAndPassword: SignUpWithEmailAndPasswordUseCase
) : BaseViewModel<SignUpState, SignUpSideEffect, SignUpEvent>(SignUpState()) {
    override fun onEvent(event: SignUpEvent) {
        when (event) {
            is SignUpEvent.EmailChanged -> updateState { copy(email = event.value) }
            is SignUpEvent.PasswordChanged -> updateState { copy(password = event.value) }
            is SignUpEvent.SignUpWithEmailAndPassword ->
                onSignUpWithEmailAndPassword(email = event.email, password = event.password)
        }
    }

    private fun onSignUpWithEmailAndPassword(email: String, password: String) {
        viewModelScope.launch {
            signUpWithEmailAndPassword(email = email, password = password).collect { resource ->
                when (resource) {
                    is Resource.Loading -> updateState { copy(isLoading = resource.isLoading()) }
                    is Resource.Success -> sendEffect(SignUpSideEffect.NavigateToHome)
                    is Resource.Error -> sendEffect(
                        SignUpSideEffect.ShowError(
                            resource.error.toGenericString()
                        )
                    )
                }
            }
        }
    }
}
