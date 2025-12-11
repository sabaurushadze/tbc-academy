package com.example.academy_tbc.presentation.screen.home

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.academy_tbc.domain.model.pc_parts.PcPartsQuery
import com.example.academy_tbc.domain.usecase.pc_parts.CalculateSalePriceUseCase
import com.example.academy_tbc.domain.usecase.pc_parts.paging.GetPcPartsPagingUseCase
import com.example.academy_tbc.presentation.common.view.BaseViewModel
import com.example.academy_tbc.presentation.screen.home.mapper.toUi
import com.example.academy_tbc.presentation.screen.home.model.PcPartUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
//    private val getPcParts: GetPcPartsUseCase,
    private val calculateSalePrice: CalculateSalePriceUseCase,
    private val getPcPartsPagingUseCase: GetPcPartsPagingUseCase,
//    private val searchPcParts: SearchPcPartsUseCase,
//    private val getPcPartsByCategory: GetPartsByCategoryUseCase,
//    private val authRepository: AuthRepository,

) : BaseViewModel<HomeState, HomeSideEffect, HomeEvent>(HomeState()) {

    companion object {
        private const val PAGE_SIZE = 10
    }

    override fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.GetParts -> {}
            is HomeEvent.Search -> search(event.query)
            is HomeEvent.GetPartsByCategory -> search(event.query)
        }
    }


    private fun search(query: PcPartsQuery) {
        updateState { copy(query = query) }
    }

    val partsPagingFlow: Flow<PagingData<PcPartUi>> =
        state
            .map { it.query }
            .distinctUntilChanged()
            .flatMapLatest { query ->
                getPcPartsPagingUseCase(pageSize = PAGE_SIZE, query = query)
                    .map { pagingData ->
                        pagingData.map { domainPcPart ->
                            val finalPrice =
                                calculateSalePrice(domainPcPart.price, domainPcPart.discount)
                            domainPcPart.toUi(finalPrice)
                        }
                    }
                    .cachedIn(viewModelScope)
            }

//    val partsPagingFlow: Flow<PagingData<PcPartUi>> =
//        getPcPartsPagingUseCase(pageSize = PAGE_SIZE, query = state.value.query)
//            .map { pagingData ->
//                pagingData.map { domainPcPart ->
//                    val finalPrice = calculateSalePrice(domainPcPart.price, domainPcPart.discount)
//                    domainPcPart.toUi(finalPrice)
//                }
//            }
//            .cachedIn(viewModelScope)

//    private fun signOut() {
//        viewModelScope.launch {
//            authRepository.signOut().collect { result ->
//                when (result) {
//                    is Resource.Error -> sendEffect(HomeSideEffect.ShowError(result.error))
//                    is Resource.Loading -> updateState { copy(isLoading = result.isLoading) }
//                    is Resource.Success -> {}
////                        sendEffect(HomeSideEffect.NavigateToSignIn)
//                }
//            }
//        }
//    }

//    private fun getPartsByCategory(query: String) {
//        viewModelScope.launch {
//            getPcPartsByCategory(query).collectLatest { result ->
//                when (result) {
//                    is Resource.Loading -> updateState { copy(isLoading = result.isLoading) }
//                    is Resource.Success -> {
//                        val uiList = result.data.map { domain ->
//                            val finalPrice = calculateSalePrice(domain.price, domain.discount)
//                            domain.toUi(finalPrice)
//                        }
//                        updateState {
//                            copy(pcParts = uiList)
//                        }
//                    }
//
//                    is Resource.Error -> sendEffect(HomeSideEffect.ShowError(result.error))
//                }
//            }
//        }
//    }
//
//    private fun search(query: String) {
//        viewModelScope.launch {
//            searchPcParts(query).collectLatest { result ->
//                when (result) {
//                    is Resource.Loading -> updateState { copy(isLoading = result.isLoading) }
//                    is Resource.Success -> {
//                        val uiList = result.data.map { domain ->
//                            val finalPrice = calculateSalePrice(domain.price, domain.discount)
//                            domain.toUi(finalPrice)
//                        }
//                        updateState {
//                            copy(pcParts = uiList)
//                        }
//                    }
//
//                    is Resource.Error -> sendEffect(HomeSideEffect.ShowError(result.error))
//                }
//            }
//        }
//    }
//
//
//
//    private fun getParts() {
//        viewModelScope.launch {
//            getPcParts().collect { result ->
//                when (result) {
//                    is Resource.Loading -> updateState { copy(isLoading = result.isLoading) }
//                    is Resource.Success -> {
//                        val uiList = result.data.map { domain ->
//                            val finalPrice =
//                                calculateSalePrice(domain.price, domain.discount)
//                            domain.toUi(finalPrice)
//                        }
//                        updateState {
//                            copy(pcParts = uiList)
//                        }
//                    }
//
//                    is Resource.Error -> sendEffect(HomeSideEffect.ShowError(result.error))
//                }
//            }
//        }
//    }
}