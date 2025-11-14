package com.example.academy_tbc.presentation.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.academy_tbc.AuthApplication
import com.example.academy_tbc.data.auth.AuthRepository
import com.example.academy_tbc.data.auth.UserTokenRepository
import com.example.academy_tbc.presentation.screen.home.state.HomeError
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okio.IOException

class HomeViewModel(
    private val networkAuthRepository: AuthRepository, val userTokenRepository: UserTokenRepository
) : ViewModel() {
    private val _homeState = MutableStateFlow<HomeUiState?>(HomeUiState.Idle)
    val homeUiState: StateFlow<HomeUiState?> = _homeState.asStateFlow()

    fun resetState() {
        _homeState.value = null
    }

    fun getUsers() {
        viewModelScope.launch {
            _homeState.value = HomeUiState.Loading
            try {
                val response = networkAuthRepository.getUsers()
                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null) {
                    _homeState.value = HomeUiState.Success(responseBody.total)
                } else if (response.code() == 400) {
                    _homeState.value = HomeUiState.Error(HomeError.EXCEPTION_USER_NOT_FOUND)
                }
            } catch (e: Exception) {
                when (e) {
                    is IOException -> _homeState.value =
                        HomeUiState.Error(HomeError.EXCEPTION_NETWORK)

                    else -> _homeState.value = HomeUiState.Error(HomeError.EXCEPTION_UNKNOWN)
                }
            }
        }
    }

    suspend fun removeUserToken() {
        userTokenRepository.removeToken()
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as AuthApplication)
                val authRepository = application.container.authRepository
                val userTokenRepository = application.container.userTokenRepository
                HomeViewModel(
                    networkAuthRepository = authRepository,
                    userTokenRepository = userTokenRepository
                )
            }
        }
    }
}

sealed interface HomeUiState {
    data class Success(val users: Int) : HomeUiState
    data class Error(val message: HomeError) : HomeUiState
    object Loading : HomeUiState
    object Idle : HomeUiState
}
