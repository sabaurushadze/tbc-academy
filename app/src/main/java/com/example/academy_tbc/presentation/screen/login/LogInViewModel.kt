package com.example.academy_tbc.presentation.screen.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.academy_tbc.data.common.Resource
import com.example.academy_tbc.data.repository.LogInRepository
import com.example.academy_tbc.data.repository.UserDataStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class LogInViewModel @Inject constructor(
    private val userDataStoreRepository: UserDataStoreRepository,
    private val logInRepository: LogInRepository
) : ViewModel() {
    private val _state = MutableStateFlow(LogInState())
    val state: StateFlow<LogInState> = _state.asStateFlow()

    private val _sideEffect = MutableSharedFlow<LogInSideEffect>()
    val sideEffect = _sideEffect.asSharedFlow()

    fun onEvent(event: LogInEvent) {
        when (event) {
            is LogInEvent.LogIn -> logIn(
                email = event.email, password = event.password
            )
        }
    }

    private fun logIn(
        email: String, password: String
    ) {
        viewModelScope.launch {
            logInRepository.logIn(email = email, password = password).collect { result ->
                when (result) {
                    is Resource.Loading -> _state.update { it.copy(isLoading = result.isLoading) }
                    is Resource.Success -> {
                        userDataStoreRepository.saveToken(result.data.token)
                        _sideEffect.emit(LogInSideEffect.NavigateToHome)
                    }

                    is Resource.Error -> _sideEffect.emit(LogInSideEffect.ShowError(result.errorMessage))
                }
            }
        }
    }
}
