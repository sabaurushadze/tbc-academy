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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException

class HomeViewModel(
    private val networkAuthRepository: AuthRepository, val userTokenRepository: UserTokenRepository
) : ViewModel() {
    private val _homeState = MutableStateFlow<HomeUiState>(HomeUiState.Idle)
    val homeUiState: StateFlow<HomeUiState> = _homeState.asStateFlow()


    fun getUsers() {
        viewModelScope.launch {
            try {
                _homeState.value = HomeUiState.Loading
                val response = networkAuthRepository.getUsers()
                if (response.isSuccessful && response.body() != null) {
                    _homeState.value = HomeUiState.Success(response.body()!!.total)
                } else {
                    _homeState.value = HomeUiState.Error
                }
            } catch (_: IOException) {
                _homeState.value = HomeUiState.Error
            } catch (_: HttpException) {
                _homeState.value = HomeUiState.Error
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
    data class Success(val total: Int) : HomeUiState

    object Error : HomeUiState
    object Loading : HomeUiState
    object Idle : HomeUiState
}
