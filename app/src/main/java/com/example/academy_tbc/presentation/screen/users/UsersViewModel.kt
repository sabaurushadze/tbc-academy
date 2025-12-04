package com.example.academy_tbc.presentation.screen.users

import androidx.lifecycle.viewModelScope
import com.example.academy_tbc.domain.common.Resource
import com.example.academy_tbc.domain.observer.ConnectivityObserver
import com.example.academy_tbc.domain.usecase.users.DeleteUserUseCase
import com.example.academy_tbc.domain.usecase.users.GetLocalUsersUseCase
import com.example.academy_tbc.domain.usecase.users.RefreshUsersUseCase
import com.example.academy_tbc.presentation.common.mapper.toMessage
import com.example.academy_tbc.presentation.common.view.BaseViewModel
import com.example.academy_tbc.presentation.screen.users.mapper.toPresentation
import com.example.academy_tbc.presentation.screen.users.model.UserUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(
    private val refreshUsers: RefreshUsersUseCase,
    private val getLocalUsers: GetLocalUsersUseCase,
    private val deleteUser: DeleteUserUseCase,
    private val connectivity: ConnectivityObserver,
) : BaseViewModel<UsersState, UsersSideEffect, UsersEvent>(UsersState()) {

    init {
        observeLocalUsers()
        observeConnectivity()
    }

    override fun onEvent(event: UsersEvent) {
        when (event) {
            is UsersEvent.DeleteUser -> onUserLongClick(event.user)
        }
    }

    private fun onUserLongClick(user: UserUi) {
        viewModelScope.launch {
            deleteUser(user.id)
        }

    }

    private fun observeConnectivity() {
        viewModelScope.launch {
            connectivity.isConnected.distinctUntilChanged().collect { online ->
                    if (online) {
                        refresh()
                        sendEffect(UsersSideEffect.ShowOnline)
                    } else {
                        sendEffect(UsersSideEffect.ShowOffline)
                    }
                }
        }
    }

    private fun observeLocalUsers() {
        viewModelScope.launch {
            getLocalUsers().collect { users ->
                updateState { copy(users = users.map { it.toPresentation() }) }
            }
        }
    }

    private fun refresh() {
        viewModelScope.launch {
            updateState { copy(isLoading = true) }

            when (val result = refreshUsers()) {
                is Resource.Error -> {
                    sendEffect(UsersSideEffect.ShowError(result.error.toMessage()))
                }

                else -> Unit
            }

            updateState { copy(isLoading = false) }
        }
    }
}