package com.example.academy_tbc.presentation.screen.location

import com.example.academy_tbc.domain.usecase.location.GetLocationUseCase
import com.example.academy_tbc.presentation.common.mapper.toGenericString
import com.example.academy_tbc.presentation.common.view.BaseViewModel
import com.example.academy_tbc.presentation.screen.location.mapper.toPresentation
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val getLocationUseCase: GetLocationUseCase,
) : BaseViewModel<MapState, MapSideEffect, MapEvent>(MapState()) {

    override fun onEvent(event: MapEvent) {
        when (event) {
            is MapEvent.GetLocation -> getLocation()
            is MapEvent.ShowAllMarkers -> showAllMarkers()

        }
    }

    private fun showAllMarkers() {
        val items = state.value.locations
        if (items.isNotEmpty()) {
            sendEffect(MapSideEffect.ZoomToBounds(items))
        }
    }

    private fun getLocation() {
        launchResource(
            flow = getLocationUseCase(),
            onLoading = { updateState { copy(isLoading = it) } },
            onSuccess = { location ->
                updateState {
                    copy(locations = location.toPresentation())
                }
            },
            onError = { sendEffect(MapSideEffect.ShowError(it.toGenericString())) }
        )
    }
}