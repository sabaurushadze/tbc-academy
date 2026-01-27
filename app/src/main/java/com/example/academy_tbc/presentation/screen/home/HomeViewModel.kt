package com.example.academy_tbc.presentation.screen.home

import androidx.lifecycle.viewModelScope
import com.example.academy_tbc.domain.common.onFailure
import com.example.academy_tbc.domain.common.onSuccess
import com.example.academy_tbc.domain.usecase.location.GetLocationsUseCase
import com.example.academy_tbc.presentation.common.BaseViewModel
import com.example.academy_tbc.presentation.screen.home.mapper.toPresentation
import com.example.academy_tbc.presentation.util.toStringResId
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
            getLocationsUseCase()
                .onSuccess { locationsDomain ->
                    updateState { copy(locations = locationsDomain.map { it.toPresentation() }) }
                }
                .onFailure { emitSideEffect(HomeSideEffect.ShowSnackBar(errorRes = it.toStringResId())) }
        }

    }


}