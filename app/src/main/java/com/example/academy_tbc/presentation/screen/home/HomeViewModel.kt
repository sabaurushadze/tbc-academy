package com.example.academy_tbc.presentation.screen.home

import androidx.lifecycle.viewModelScope
import com.example.academy_tbc.domain.resource.Resource
import com.example.academy_tbc.domain.usecase.home.GetLocationsUseCase
import com.example.academy_tbc.presentation.common.mapper.toMessage
import com.example.academy_tbc.presentation.common.view.BaseViewModel
import com.example.academy_tbc.presentation.screen.home.mapper.toUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getLocationsUseCase: GetLocationsUseCase,
) : BaseViewModel<HomeState, HomeSideEffect, HomeEvent>(HomeState()) {


    override fun onEvent(event: HomeEvent) {
        when (event) {
            HomeEvent.GetLocations -> getLocations()
        }
    }

    private fun getLocations() {
        viewModelScope.launch {
            getLocationsUseCase().collect { result ->
                when (result) {
                    is Resource.Loading -> updateState { copy(isLoading = result.isLoading) }
                    is Resource.Success -> updateState { copy(locations = result.data.map { it.toUi() }) }
                    is Resource.Error -> sendEffect(HomeSideEffect.ShowError(result.error.toMessage()))
                }
            }
        }
    }
}