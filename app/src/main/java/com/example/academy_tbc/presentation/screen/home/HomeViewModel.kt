package com.example.academy_tbc.presentation.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@HiltViewModel
class HomeViewModel @Inject constructor(
    getUsersPagingUseCase: GetUsersPagingUseCase,
) : ViewModel() {

    val usersFlow: Flow<PagingData<UserModel.User>> = getUsersPagingUseCase(
        PagingConfig(
            pageSize = PAGE_SIZE,
            enablePlaceholders = false
        )
    ).map { pagingData ->
        pagingData.map { it.toPresentation() }
    }.cachedIn(viewModelScope)


    companion object {
        private const val PAGE_SIZE = 6
    }
}