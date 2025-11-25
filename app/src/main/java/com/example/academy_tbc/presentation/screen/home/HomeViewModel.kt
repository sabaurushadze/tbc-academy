package com.example.academy_tbc.presentation.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.academy_tbc.data.manager.ConnectivityObserver
import com.example.academy_tbc.data.repository.UsersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.stateIn

@HiltViewModel
class HomeViewModel @Inject constructor(
    usersRepository: UsersRepository,
    connectivityObserver: ConnectivityObserver,
) : ViewModel() {
    val usersPager = usersRepository.getUsersPager()
        .flow
        .cachedIn(viewModelScope)

    val isConnected = connectivityObserver.isConnected
        .distinctUntilChanged()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            false
        )
}