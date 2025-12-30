package com.example.academy_tbc.presentation.common.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.academy_tbc.domain.common.Resource
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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


    protected fun <T: Any> handleResponse(
        apiCall: () -> Flow<Resource<T>>,
        onSuccess: (T) -> Unit,
        onError: (Resource.Error) -> Unit,
        onLoading: (Boolean) -> Unit,
    ) {
        viewModelScope.launch {
            apiCall.invoke().collect { resource ->
                getResourceType(
                    resource = resource,
                    onSuccess = onSuccess,
                    onError = onError,
                    onLoading = onLoading
                )
            }
        }
    }

    private fun <T: Any> getResourceType(
        resource: Resource<T>,
        onSuccess: (T) -> Unit,
        onError: (Resource.Error) -> Unit,
        onLoading: (Boolean) -> Unit,
    ) {
        when (resource) {
            is Resource.Success -> onSuccess(resource.data)
            is Resource.Error -> onError(resource)
            is Resource.Loading -> onLoading(resource.isLoading)
        }
    }
}