package com.example.challenge.presentation.screen.connection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.challenge.data.common.Resource
import com.example.challenge.domain.usecase.connection.GetConnectionsUseCase
import com.example.challenge.domain.usecase.datastore.ClearDataStoreUseCase
import com.example.challenge.presentation.screen.connection.mapper.toPresentation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConnectionsViewModel @Inject constructor(
    private val getConnectionsUseCase: GetConnectionsUseCase,
    private val clearDataStoreUseCase: ClearDataStoreUseCase,
) :
    ViewModel() {
    private val _connectionState = MutableStateFlow(ConnectionState())
    val connectionState: StateFlow<ConnectionState> = _connectionState.asStateFlow()

    private val _sideEffect by lazy { Channel<ConnectionSideEffect>() }
    val sideEffect = _sideEffect.receiveAsFlow()


    fun onEvent(event: ConnectionEvent) {
        when (event) {
            is ConnectionEvent.FetchConnections -> fetchConnections()
            is ConnectionEvent.LogOut -> logOut()
            is ConnectionEvent.ResetErrorMessage -> updateErrorMessage(message = null)
        }
    }

    private fun fetchConnections() {
        viewModelScope.launch {
            getConnectionsUseCase().collect { result ->
                when (result) {
                    is Resource.Loading -> _connectionState.update { currentState ->
                        currentState.copy(
                            isLoading = result.loading
                        )
                    }

                    is Resource.Success -> {
                        _connectionState.update { currentState -> currentState.copy(connections = result.data.map { it.toPresentation() }) }
                    }

                    is Resource.Error -> updateErrorMessage(message = result.errorMessage)
                }
            }
        }
    }

    private fun logOut() {
        viewModelScope.launch {
            clearDataStoreUseCase()
            _sideEffect.send(ConnectionSideEffect.NavigateToLogIn)
        }
    }

    private fun updateErrorMessage(message: String?) {
        _connectionState.update { currentState -> currentState.copy(errorMessage = message) }
    }

}