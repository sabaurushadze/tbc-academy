package com.example.academy_tbc.presentation.screen.home

import androidx.lifecycle.viewModelScope
import com.example.academy_tbc.domain.common.onFailure
import com.example.academy_tbc.domain.common.onSuccess
import com.example.academy_tbc.domain.usecase.user.GetUsersUseCase
import com.example.academy_tbc.presentation.common.BaseViewModel
import com.example.academy_tbc.presentation.screen.home.users.mapper.toPresentation
import com.example.academy_tbc.presentation.util.toStringResId
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getUsersUseCase: GetUsersUseCase,
) : BaseViewModel<HomeState, HomeSideEffect, HomeEvent>(HomeState()) {

    override fun onEvent(event: HomeEvent) {
        when (event) {

            is HomeEvent.GetUsers -> getUsers(event.query)
            is HomeEvent.QueryChanged -> updateQuery(event.query)
        }
    }

    private fun updateQuery(query: String?) {
        query?.let {
            updateState { copy(query = query) }
        }
    }

    private fun getUsers(query: String? = null) = viewModelScope.launch {
        updateState { copy(isLoading = true) }

        getUsersUseCase(query)
            .onSuccess { usersDomain ->
                updateState {
                    copy(
                        users = usersDomain.map { it.toPresentation() },
                        isLoading = false
                    )
                }
            }
            .onFailure {
                emitSideEffect(HomeSideEffect.ShowSnackBar(it.toStringResId()))
                updateState { copy(isLoading = false) }
            }
    }
}