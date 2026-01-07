package com.example.academy_tbc.presentation.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.academy_tbc.domain.usecase.home.GetUsersPagingUseCase
import com.example.academy_tbc.presentation.screen.home.mapper.UiUserMapper
import com.example.academy_tbc.presentation.screen.home.model.UserModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    getUsersPagingUseCase: GetUsersPagingUseCase,
    private val uiUserMapper: UiUserMapper,
) : ViewModel() {

    val usersFlow: Flow<PagingData<UserModel.User>> = getUsersPagingUseCase(
        PagingConfig(
            pageSize = PAGE_SIZE,
            enablePlaceholders = false
        )
    ).map { pagingData ->
        pagingData.map(uiUserMapper::mapFromDomain)
    }.cachedIn(viewModelScope)


    companion object {
        private const val PAGE_SIZE = 6
    }
}