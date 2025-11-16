package com.example.academy_tbc.presentation.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.academy_tbc.data.auth.UsersRepository
import com.example.academy_tbc.data.auth.home.ResponseUserDto
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okio.IOException

class HomeViewModel(
    private val networkUsersRepository: UsersRepository,
) : ViewModel() {
    private val _homeState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val homeUiState: StateFlow<HomeUiState> = _homeState.asStateFlow()

    init {
        getUsers()
    }

    private var homeJob: Job? = null
    private fun getUsers() {
        if (homeJob != null) return

        homeJob = viewModelScope.launch {
            try {
                _homeState.value = HomeUiState.Loading
                val response = networkUsersRepository.getUsers()
                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null) {
                    val usersList = responseBody.data
                    _homeState.value = HomeUiState.Success(usersList)
                } else if (response.code() == 400) {
                    _homeState.value = HomeUiState.Error(HomeError.EXCEPTION_USER_NOT_FOUND)
                }
            } catch (e: Exception) {
                when (e) {
                    is IOException -> _homeState.value =
                        HomeUiState.Error(HomeError.EXCEPTION_NETWORK)

                    else -> _homeState.value = HomeUiState.Error(HomeError.EXCEPTION_UNKNOWN)
                }
            } finally {
                homeJob = null
            }
        }
    }
}

sealed class HomeUiState {
    data class Success(val users: List<ResponseUserDto>?) : HomeUiState()
    data class Error(val error: HomeError) : HomeUiState()
    object Loading : HomeUiState()
}