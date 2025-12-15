package com.example.academy_tbc.presentation.screen.home

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.academy_tbc.domain.common.Resource
import com.example.academy_tbc.domain.common.isLoading
import com.example.academy_tbc.domain.usecase.categories.GetCategoriesUseCase
import com.example.academy_tbc.domain.usecase.pc_parts.CalculateSalePriceUseCase
import com.example.academy_tbc.domain.usecase.pc_parts.GetPcPartsPagingUseCase
import com.example.academy_tbc.presentation.common.view.BaseViewModel
import com.example.academy_tbc.presentation.screen.home.category.mapper.toGenericString
import com.example.academy_tbc.presentation.screen.home.category.mapper.toUi
import com.example.academy_tbc.presentation.screen.home.mapper.toDomain
import com.example.academy_tbc.presentation.screen.home.mapper.toUi
import com.example.academy_tbc.presentation.screen.home.model.PcPartUi
import com.example.academy_tbc.presentation.screen.home.model.PcPartsQueryUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val calculateSalePrice: CalculateSalePriceUseCase,
    private val getPcPartsPagingUseCase: GetPcPartsPagingUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase,
) : BaseViewModel<HomeState, HomeSideEffect, HomeEvent>(HomeState()) {

    init {
        getCategories()
    }

    companion object {
        private const val PAGE_SIZE = 10
    }



    override fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.GetParts -> {}
            is HomeEvent.Search -> search(event.query)
            is HomeEvent.GetPartsByCategory -> search(event.query)
            is HomeEvent.SaveCategory -> saveCategory(event.category)
        }
    }

    private fun saveCategory(categoryId: Int) {
        updateState {
            val updatedCategories = categories.map {
                it.copy(selected = it.id == categoryId)
            }
            copy(
                category = categoryId,
                categories = updatedCategories
            )
        }
    }

    private fun getCategories() {
        viewModelScope.launch {
            getCategoriesUseCase().collect { result ->
                when (result) {
                    is Resource.Loading -> updateState { copy(isLoading = result.isLoading()) }
                    is Resource.Success -> {
                        updateState { copy(categories = result.data.map { it.toUi().copy(selected = it.id == category) }) }
                    }

                    is Resource.Error -> sendEffect(HomeSideEffect.ShowError(result.error.toGenericString()))
                }
            }
        }
    }

    private fun search(query: PcPartsQueryUi) {
        updateState { copy(query = query) }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val partsPagingFlow: Flow<PagingData<PcPartUi>> =
        state
            .map { it.query }
            .distinctUntilChanged()
            .flatMapLatest { query ->
                getPcPartsPagingUseCase(pageSize = PAGE_SIZE, query = query.toDomain())
                    .map { pagingData ->
                        pagingData.map { domainPcPart ->
                            val finalPrice =
                                calculateSalePrice(domainPcPart.price, domainPcPart.discount)
                            domainPcPart.toUi(finalPrice)
                        }
                    }
                    .cachedIn(viewModelScope)
            }
}