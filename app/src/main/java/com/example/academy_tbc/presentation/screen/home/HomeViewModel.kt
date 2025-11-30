package com.example.academy_tbc.presentation.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.academy_tbc.domain.usecase.network.ObserveConnectivityUseCase
import com.example.academy_tbc.domain.usecase.users.GetUsersPagingUseCase
import com.example.academy_tbc.presentation.screen.home.mapper.toPresentation
import com.example.academy_tbc.presentation.screen.home.model.UserModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@HiltViewModel
class HomeViewModel @Inject constructor(
    getUsersPagingUseCase: GetUsersPagingUseCase,
    observeConnectivityUseCase: ObserveConnectivityUseCase,
) : ViewModel() {

    val usersFlow: Flow<PagingData<UserModel.User>> = getUsersPagingUseCase().map { pagingData ->
        pagingData.map { it.toPresentation() }
    }.cachedIn(viewModelScope)

    val isConnected = observeConnectivityUseCase().stateIn(
            viewModelScope, SharingStarted.WhileSubscribed(5000L), false
        )
}