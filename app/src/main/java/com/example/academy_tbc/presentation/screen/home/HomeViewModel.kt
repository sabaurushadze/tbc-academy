package com.example.academy_tbc.presentation.screen.home

import android.util.Log.d
import com.example.academy_tbc.domain.common.onFailure
import com.example.academy_tbc.domain.common.onSuccess
import com.example.academy_tbc.domain.usecase.category.GetCategoriesUseCase
import com.example.academy_tbc.domain.usecase.outfit.GetOutfitsByCategoryIdUseCase
import com.example.academy_tbc.domain.usecase.outfit.GetOutfitsUseCase
import com.example.academy_tbc.presentation.common.BaseViewModel
import com.example.academy_tbc.presentation.screen.home.category.mapper.toPresentation
import com.example.academy_tbc.presentation.screen.home.outfit.mapper.toPresentation
import com.example.academy_tbc.presentation.util.toStringResId
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val getOutfitsUseCase: GetOutfitsUseCase,
    private val getOutfitsByIdUseCase: GetOutfitsByCategoryIdUseCase,
) : BaseViewModel<HomeState, HomeSideEffect, HomeEvent>(HomeState()) {

    init {
        getCategories()
        getOutfits()
    }

//    override fun setLoading(isLoading: Boolean) = updateState { copy(isLoading = isLoading) }

    override fun onEvent(event: HomeEvent) {
        when (event) {
            HomeEvent.GetCategories -> getCategories()
            HomeEvent.GetOutfits -> getOutfits()
            is HomeEvent.CategoryClicked -> getOutfitsById(event.id)
        }
    }

    private fun getCategories() = launchWithLoading {
        getCategoriesUseCase()
            .onSuccess { categoriesDomain ->
                updateState { copy(categories = categoriesDomain.map { it.toPresentation() }) }
            }
            .onFailure { emitSideEffect(HomeSideEffect.ShowSnackBar(it.toStringResId())) }
    }

    private fun getOutfits() = launchWithLoading {
        getOutfitsUseCase()
            .onSuccess { outfitsDomain ->
                updateState { copy(outfits = outfitsDomain.map { it.toPresentation() }) }
            }
            .onFailure { emitSideEffect(HomeSideEffect.ShowSnackBar(it.toStringResId())) }
    }

    private fun getOutfitsById(id: Int) = launchWithLoading {
        getOutfitsByIdUseCase(id)
            .onSuccess { outfitsDomain ->
                updateState {
                    copy(
                        outfits = outfitsDomain.map { it.toPresentation() },
                        selectedCategoryId = id
                    )
                }
            d("asdd", "pressed id is: $id")
            }
            .onFailure { emitSideEffect(HomeSideEffect.ShowSnackBar(it.toStringResId())) }
    }
}