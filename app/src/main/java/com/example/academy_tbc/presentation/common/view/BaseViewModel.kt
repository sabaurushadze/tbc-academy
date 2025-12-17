package com.example.academy_tbc.presentation.common.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.academy_tbc.domain.common.Resource
import com.example.academy_tbc.domain.common.ResourceError
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class BaseViewModel<STATE, EFFECT, EVENT>(
    initialState: STATE,
) : ViewModel() {

    private val _state = MutableStateFlow(initialState)
    val state: StateFlow<STATE> = _state

    protected fun updateState(reduce: STATE.() -> STATE) {
        _state.update(reduce)
    }

    private val _effect = Channel<EFFECT>()
    val effect = _effect.receiveAsFlow()

    protected fun sendEffect(effect: EFFECT) {
        viewModelScope.launch {
            _effect.send(effect)
        }
    }

    open fun onEvent(event: EVENT) {}

    protected fun <T, E : ResourceError> launchResource(
        flow: Flow<Resource<T, E>>,
        onLoading: (Boolean) -> Unit = {},
        onSuccess: (T) -> Unit = {},
        onError: (E) -> Unit = {}
    ) {
        viewModelScope.launch {
            flow.collectLatest { result ->
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