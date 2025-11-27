package com.example.academy_tbc.presentation.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.academy_tbc.data.manager.ConnectivityObserver
import com.example.academy_tbc.data.remote.home.paging.UsersPagingSource
import com.example.academy_tbc.data.remote.home.repository.UsersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val usersRepository: UsersRepository,
    connectivityObserver: ConnectivityObserver,
) : ViewModel() {
    val usersPager = Pager(
        PagingConfig(pageSize = 6)
    ) {
        UsersPagingSource(usersRepository)
    }.flow.cachedIn(viewModelScope)

    val isConnected = connectivityObserver.isConnected.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000L), false
    )
}