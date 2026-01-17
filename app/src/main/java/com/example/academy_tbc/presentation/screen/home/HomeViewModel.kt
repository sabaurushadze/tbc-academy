package com.example.academy_tbc.presentation.screen.home

import androidx.lifecycle.viewModelScope
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
import kotlinx.coroutines.launch
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

    override fun onEvent(event: HomeEvent) {
        when (event) {
            HomeEvent.GetCategories -> getCategories()
            HomeEvent.GetOutfits -> getOutfits()
            is HomeEvent.CategoryClicked -> getOutfitsById(event.id)
            is HomeEvent.FavoriteClicked -> saveFavoriteOutfit(event.id)
        }
    }

    private fun saveFavoriteOutfit(id: Int) {
        updateState {
            val updatedFavorites =
                if (favoriteOutfits.contains(id)) {
                    favoriteOutfits - id
                } else {
                    favoriteOutfits + id
                }
            copy(favoriteOutfits = updatedFavorites)
        }
    }

    private fun getCategories() = viewModelScope.launch {
        updateState { copy(isLoading = true) }

        getCategoriesUseCase()
            .onSuccess { categoriesDomain ->
                updateState { copy(categories = categoriesDomain.map { it.toPresentation() }) }
                updateState { copy(isLoading = false) }
            }
            .onFailure {
                emitSideEffect(HomeSideEffect.ShowSnackBar(it.toStringResId()))
                updateState { copy(isLoading = false) }
            }
    }

    private fun getOutfits() = viewModelScope.launch {
        updateState { copy(isLoading = true) }
        getOutfitsUseCase()
            .onSuccess { outfitsDomain ->
                updateState { copy(outfits = outfitsDomain.map { it.toPresentation() }) }
                updateState { copy(isLoading = false) }
            }
            .onFailure {
                emitSideEffect(HomeSideEffect.ShowSnackBar(it.toStringResId()))
                updateState { copy(isLoading = false) }
            }
    }

    private fun getOutfitsById(id: Int) = viewModelScope.launch {
        updateState { copy(isLoading = true) }
        getOutfitsByIdUseCase(id)
            .onSuccess { outfitsDomain ->
                updateState {
                    copy(
                        outfits = outfitsDomain.map { it.toPresentation() },
                        selectedCategoryId = id,
                    )
                }
                updateState { copy(isLoading = false) }
            }
            .onFailure {
                emitSideEffect(HomeSideEffect.ShowSnackBar(it.toStringResId()))
                updateState { copy(isLoading = false) }
            }
    }
}