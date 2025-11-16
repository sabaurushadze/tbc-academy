package com.example.academy_tbc.presentation.screen.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.academy_tbc.data.auth.UserTokenRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val userTokenRepository: UserTokenRepository
) : ViewModel() {
    private val _profileUiState = MutableStateFlow(ProfileUiState(null))
    val profileUiState: StateFlow<ProfileUiState> = _profileUiState.asStateFlow()

    init {
        viewModelScope.launch {
            userTokenRepository.getEmail.collect { email ->
                _profileUiState.update {
                    it.copy(email)
                }
            }
        }
    }

    suspend fun removeUserToken() {
        userTokenRepository.removeToken()
        userTokenRepository.removeEmail()
    }
}

data class ProfileUiState(
    val email: String?
)