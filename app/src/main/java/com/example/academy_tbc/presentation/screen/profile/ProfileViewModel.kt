package com.example.academy_tbc.presentation.screen.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.academy_tbc.domain.usecase.datastore.ClearPreferencesUseCase
import com.example.academy_tbc.domain.usecase.datastore.GetEmailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getEmailUseCase: GetEmailUseCase,
    private val clearPreferencesUseCase: ClearPreferencesUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(ProfileState())
    val state = _state.asStateFlow()
    private val _sideEffect = MutableSharedFlow<ProfileSideEffect>()
    val sideEffect = _sideEffect.asSharedFlow()


    fun onEvent(event: ProfileEvent) {
        when (event) {
            ProfileEvent.RemoveUserToken -> removeUserToken()
            is ProfileEvent.GetUserEmail -> getUserEmail()
        }
    }

    private fun getUserEmail() {
        viewModelScope.launch {
            _state.update {
                it.copy(email = getEmailUseCase())
            }
        }
    }

    private fun removeUserToken() {
        viewModelScope.launch {
            clearPreferencesUseCase()
            _sideEffect.emit(ProfileSideEffect.NavigateToLogIn)
        }
    }
}