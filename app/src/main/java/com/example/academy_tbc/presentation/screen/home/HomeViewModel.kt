package com.example.academy_tbc.presentation.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.academy_tbc.data.common.Resource
import com.example.academy_tbc.data.repository.UsersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val usersRepository: UsersRepository
) : ViewModel() {
    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state.asStateFlow()

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.GetUsers -> getUsers()
        }
    }

    private fun getUsers() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = "") }

            usersRepository.getUsers().collect { result ->
                when (result) {
                    is Resource.Success -> _state.update {
                        it.copy(
                            users = result.data.data, error = ""
                        )
                    }

                    is Resource.Loading -> _state.update { it.copy(isLoading = result.isLoading) }
                    is Resource.Error -> _state.update {
                        it.copy(
                            users = emptyList(), error = result.errorMessage
                        )
                    }
                }
            }
        }
    }
}