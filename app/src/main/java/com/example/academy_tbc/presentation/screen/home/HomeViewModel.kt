package com.example.academy_tbc.presentation.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.academy_tbc.data.auth.UserTokenRepository
import com.example.academy_tbc.data.auth.UsersRepository
import com.example.academy_tbc.presentation.screen.home.state.HomeError
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okio.IOException

class HomeViewModel(
    private val networkUsersRepository: UsersRepository,
    private val userTokenRepository: UserTokenRepository
) : ViewModel() {
    private val _homeState = MutableStateFlow(HomeUiState())
    val homeUiState: StateFlow<HomeUiState> = _homeState.asStateFlow()

    fun resetState() {
        _homeState.update { it.copy(isLoading = false, userCount = null, error = null) }
    }

    private var homeJob: Job? = null
    fun getUsers() {
        if (homeJob != null) return

        homeJob = viewModelScope.launch {
            try {
                _homeState.update { it.copy(isLoading = true) }
                val response = networkUsersRepository.getUsers()
                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null) {
                    _homeState.update { it.copy(userCount = responseBody.total) }
                } else if (response.code() == 400) {
                    _homeState.update { it.copy(error = HomeError.EXCEPTION_USER_NOT_FOUND) }
                }
            } catch (e: Exception) {
                when (e) {
                    is IOException -> _homeState.update {
                        it.copy(error = HomeError.EXCEPTION_NETWORK)
                    }

                    else -> _homeState.update {
                        it.copy(error = HomeError.EXCEPTION_UNKNOWN)
                    }
                }
            } finally {
                homeJob = null
            }
        }
    }

    suspend fun removeUserToken() {
        userTokenRepository.removeToken()
    }
}

data class HomeUiState(
    val isLoading: Boolean = false, val error: HomeError? = null, val userCount: Int? = null
)