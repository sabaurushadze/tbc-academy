package com.example.academy_tbc.presentation.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.academy_tbc.domain.common.Resource
import com.example.academy_tbc.domain.common.Failure
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class BaseViewModel<STATE, EFFECT, EVENT>(
    initialState: STATE,
) : ViewModel() {

    private val _state = MutableStateFlow(initialState)
    val state: StateFlow<STATE> = _state.asStateFlow()

    private val _sideEffect by lazy { Channel<EFFECT>() }
    val sideEffect = _sideEffect.receiveAsFlow()

    open fun onEvent(event: EVENT) = Unit

    protected fun updateState(block: STATE.() -> STATE) {
        _state.update(block)
    }
    protected fun emitSideEffect(sideEffect: EFFECT) {
        viewModelScope.launch {
            _sideEffect.send(sideEffect)
        }
    }


    protected fun <T: Any, E: Failure> handleResponse(
        apiCall: () -> Flow<Resource<T, E>>,
        onSuccess: (T) -> Unit,
        onError: (E) -> Unit,
        onLoading: () -> Unit,
    ) {
        viewModelScope.launch {
            apiCall().collectLatest { resource ->
                when (resource) {
                    is Resource.Success -> onSuccess(resource.data)
                    is Resource.Error -> onError(resource.error)
                    Resource.Loading -> onLoading()
                }
            }
        }
    }
}