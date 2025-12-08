package com.example.academy_tbc.presentation.screen.home

import androidx.lifecycle.viewModelScope
import com.example.academy_tbc.domain.repository.auth.AuthRepository
import com.example.academy_tbc.domain.resource.Resource
import com.example.academy_tbc.domain.usecase.pc_parts.CalculateSalePriceUseCase
import com.example.academy_tbc.domain.usecase.pc_parts.GetPcPartsUseCase
import com.example.academy_tbc.domain.usecase.pc_parts.SearchPcPartsUseCase
import com.example.academy_tbc.presentation.common.view.BaseViewModel
import com.example.academy_tbc.presentation.screen.home.mapper.toUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getPcPartsUseCase: GetPcPartsUseCase,
    private val calculateSalePriceUseCase: CalculateSalePriceUseCase,
    private val searchPcPartsUseCase: SearchPcPartsUseCase,
    private val authRepository: AuthRepository,
) : BaseViewModel<HomeState, HomeSideEffect, HomeEvent>(HomeState()) {


    override fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.GetParts -> getParts()
            is HomeEvent.Search -> search(event.query)
            HomeEvent.SignOut -> signOut()
        }
    }

    private fun signOut() {
        viewModelScope.launch {
            authRepository.signOut().collect { result ->
                when (result) {
                    is Resource.Error -> sendEffect(HomeSideEffect.ShowError(result.error))
                    is Resource.Loading -> updateState { copy(isLoading = result.isLoading) }
                    is Resource.Success -> sendEffect(HomeSideEffect.NavigateToSignIn)
                }
            }
        }
    }

    private fun search(query: String) {
        viewModelScope.launch {
            searchPcPartsUseCase(query).collectLatest { result ->
                when (result) {
                    is Resource.Loading -> updateState { copy(isLoading = result.isLoading) }
                    is Resource.Success -> {
                        val uiList = result.data.map { domain ->
                            val finalPrice =
                                calculateSalePriceUseCase(domain.price, domain.discount)
                            domain.toUi(finalPrice)
                        }
                        updateState {
                            copy(pcParts = uiList)
                        }
                    }

                    is Resource.Error -> sendEffect(HomeSideEffect.ShowError(result.error))
                }
            }
        }
    }

    private fun getParts() {
        viewModelScope.launch {
            getPcPartsUseCase().collect { result ->
                when (result) {
                    is Resource.Loading -> updateState { copy(isLoading = result.isLoading) }
                    is Resource.Success -> {
                        val uiList = result.data.map { domain ->
                            val finalPrice =
                                calculateSalePriceUseCase(domain.price, domain.discount)
                            domain.toUi(finalPrice)
                        }
                        updateState {
                            copy(pcParts = uiList)
                        }
                    }

                    is Resource.Error -> sendEffect(HomeSideEffect.ShowError(result.error))
                }
            }
        }
    }
}