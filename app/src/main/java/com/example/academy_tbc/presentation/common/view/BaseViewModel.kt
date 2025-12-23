package com.example.academy_tbc.presentation.common.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.academy_tbc.domain.common.Resource
import com.example.academy_tbc.domain.common.ResourceError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class BaseViewModel<UiState, SideEffect, Event>(
    initialUiState: UiState,
) : ViewModel() {

    private val _state = MutableStateFlow(initialUiState)
    val state: StateFlow<UiState> = _state.asStateFlow()

    private val _sideEffect by lazy { Channel<SideEffect>() }
    val sideEffect: Flow<SideEffect> by lazy { _sideEffect.receiveAsFlow() }

    open fun onEvent(event: Event) = Unit
    protected fun updateUiState(block: UiState.() -> UiState) {
        _state.update(block)
    }

    protected fun updateUiState(newUiState: UiState) {
        _state.update { newUiState }
    }


    protected fun ViewModel.emitSideEffect(effect: SideEffect) {
        viewModelScope.launch(Dispatchers.Main.immediate) {
            _sideEffect.send(effect)
        }
    }

    protected fun <T, E : ResourceError> launchResource(
        apiCall: Flow<Resource<T, E>>,
        onLoading: (Boolean) -> Unit = {},
        onSuccess: (T) -> Unit = {},
        onError: (E) -> Unit = {},
    ) {
        viewModelScope.launch {
            apiCall.collectLatest { result ->
                when (result) {
                    is Resource.Loading -> onLoading(true)
                    is Resource.Success -> {
                        onLoading(false)
                        onSuccess(result.data)
                    }

                    is Resource.Error -> {
                        onLoading(false)
                        onError(result.error)
                    }
                }
            }
        }
    }
}