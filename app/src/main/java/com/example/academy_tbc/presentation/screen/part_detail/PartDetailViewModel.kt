package com.example.academy_tbc.presentation.screen.part_detail

import androidx.lifecycle.viewModelScope
import com.example.academy_tbc.domain.common.Resource
import com.example.academy_tbc.domain.common.isLoading
import com.example.academy_tbc.domain.usecase.part_details.GetPartDetailsUseCase
import com.example.academy_tbc.domain.usecase.pc_parts.CalculateSalePriceUseCase
import com.example.academy_tbc.presentation.common.view.BaseViewModel
import com.example.academy_tbc.presentation.screen.home.category.mapper.toGenericString
import com.example.academy_tbc.presentation.screen.part_detail.mapper.toUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PartDetailViewModel @Inject constructor(
    private val calculateSalePrice: CalculateSalePriceUseCase,
    private val getPartDetailsUseCase: GetPartDetailsUseCase,
) : BaseViewModel<PartDetailState, PartDetailSideEffect, PartDetailEvent>(PartDetailState()) {
    override fun onEvent(event: PartDetailEvent) {
        when (event) {
            is PartDetailEvent.GetPartDetails -> getPartDetails(event.id)
        }
    }

    private fun getPartDetails(id: Int) {
        viewModelScope.launch {
            getPartDetailsUseCase(id).collect { resource ->
                when (resource) {
                    is Resource.Error -> {
                        sendEffect(PartDetailSideEffect.ShowError(resource.error.toGenericString()))
                    }

                    Resource.Loading -> updateState { copy(isLoading = resource.isLoading()) }
                    is Resource.Success -> updateState {
                        val finalPrice =
                            calculateSalePrice(resource.data.price, resource.data.discount)
                        copy(partDetails = resource.data.toUi(finalPrice))
                    }
                }
            }
        }
    }
}