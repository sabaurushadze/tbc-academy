package com.example.challenge.presentation.screen.log_in

import android.util.Log.d
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.challenge.data.common.Resource
import com.example.challenge.domain.usecase.datastore.SaveTokenUseCase
import com.example.challenge.domain.usecase.log_in.LogInUseCase
import com.example.challenge.domain.usecase.validator.EmailValidatorUseCase
import com.example.challenge.domain.usecase.validator.PasswordValidatorUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LogInViewModel @Inject constructor(
    private val logInUseCase: LogInUseCase,
//    private val saveTokenUseCase: SaveTokenUseCase,
    private val emailValidator: EmailValidatorUseCase,
    private val passwordValidator: PasswordValidatorUseCase,
) : ViewModel() {
    private val _logInState = MutableStateFlow(LogInState())
    val logInState: StateFlow<LogInState> = _logInState.asStateFlow()


    private val _sideEffect by lazy { Channel<LogInSideEffect>() }
    val sideEffect = _sideEffect.receiveAsFlow()

//    private val _sideEffect = MutableSharedFlow<LogInSideEffect>()
//    val sideEffect: SharedFlow<LogInSideEffect> get() = _sideEffect

    fun onEvent(event: LogInEvent) {
        when (event) {
            is LogInEvent.LogIn -> validateForm(email = event.email, password = event.password)
//            is LogInEvent.ResetErrorMessage -> updateErrorMessage(message = null)
        }
    }

    private fun logIn(email: String, password: String) {
        viewModelScope.launch {
            logInUseCase(email = email, password = password).collect { result ->
                when (result) {
                    is Resource.Loading -> _logInState.update { currentState ->
                        d("asdd", "Loading $result")
                        currentState.copy(
                            isLoading = result.loading
                        )
                    }

                    is Resource.Success -> {
                        d("asdd", "Success $result")
//                        _logInState.update { currentState -> currentState.copy(accessToken = result.data.accessToken) }
//                        saveTokenUseCase(result.data.accessToken)
                        _sideEffect.send(LogInSideEffect.NavigateToConnections)
                    }

                    is Resource.Error -> {
                        d("asdd", "Error $result")
                        updateErrorMessage(message = result.errorMessage)
                    }
                }
            }
        }
    }

    private fun validateForm(email: String, password: String) {
        val isEmailValid = emailValidator(email)
        val isPasswordValid = passwordValidator(password)

        val areFieldsValid =
            listOf(isEmailValid, isPasswordValid)
                .all { it }

        if (!areFieldsValid) {
            updateErrorMessage(message = "Fields are not valid!")
            return
        }

        logIn(email = email, password = password)
    }

    private fun updateErrorMessage(message: String?) {
        _logInState.update { currentState -> currentState.copy(errorMessage = message) }
    }
}



