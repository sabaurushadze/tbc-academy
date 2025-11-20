package com.example.academy_tbc.presentation.screen.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.academy_tbc.data.repository.UserDataStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userDataStoreRepository: UserDataStoreRepository
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
            val userEmail = userDataStoreRepository.getEmail.first()
            _state.update { it.copy(email = userEmail) }
        }
    }

    private fun removeUserToken() {
        viewModelScope.launch {
            userDataStoreRepository.removeToken()
            userDataStoreRepository.removeEmail()
            _sideEffect.emit(ProfileSideEffect.NavigateToLogIn)
        }
    }
}