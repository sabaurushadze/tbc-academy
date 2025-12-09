package com.example.academy_tbc.presentation.screen.home

import androidx.lifecycle.viewModelScope
import com.example.academy_tbc.data.room.home.location.LocationDao
import com.example.academy_tbc.data.room.home.location.mapper.toDomain
import com.example.academy_tbc.data.room.home.post.PostDao
import com.example.academy_tbc.data.room.home.post.mapper.toDomain
import com.example.academy_tbc.domain.resource.Resource
import com.example.academy_tbc.domain.usecase.home.location.GetLocationsUseCase
import com.example.academy_tbc.domain.usecase.home.post.GetPostsUseCase
import com.example.academy_tbc.presentation.common.mapper.toMessage
import com.example.academy_tbc.presentation.common.view.BaseViewModel
import com.example.academy_tbc.presentation.screen.home.mapper.toUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getLocationsUseCase: GetLocationsUseCase,
    private val getPostsUseCase: GetPostsUseCase,
    private val locationDao: LocationDao,
    private val postDao: PostDao,
) : BaseViewModel<HomeState, HomeSideEffect, HomeEvent>(HomeState()) {


    init {
        loadCachedData()
        getLocations()
        getPosts()
    }


    private fun loadCachedData() {
        viewModelScope.launch {
            val cachedLocations = locationDao.getAll()
                .map { list -> list.map { it.toDomain().toUi() } }
                .firstOrNull() ?: emptyList()
            val cachedPosts = postDao.getAll()
                .map { list -> list.map { it.toDomain().toUi() } }
                .firstOrNull() ?: emptyList()

            updateState { copy(locations = cachedLocations) }
            updateState { copy(posts = cachedPosts) }
        }
    }

    override fun onEvent(event: HomeEvent) {
        when (event) {
            HomeEvent.GetLocations -> getLocations()
            HomeEvent.GetPosts -> getPosts()
        }
    }


    private fun getPosts() {
        viewModelScope.launch {
            getPostsUseCase().collect { result ->
                when (result) {
                    is Resource.Loading -> updateState { copy(isLoading = result.isLoading) }
                    is Resource.Success -> updateState {
                        copy(
                            posts = result.data.map { it.toUi() },
                            showRetryButton = false
                        )
                    }

                    is Resource.Error -> {
                        updateState { copy(showRetryButton = true) }
                        sendEffect(HomeSideEffect.ShowError(result.error.toMessage()))
                    }
                }
            }
        }
    }

    private fun getLocations() {
        viewModelScope.launch {
            getLocationsUseCase().collect { result ->
                when (result) {
                    is Resource.Loading -> updateState { copy(isLoading = result.isLoading) }
                    is Resource.Success -> updateState {
                        copy(
                            locations = result.data.map { it.toUi() },
                            showRetryButton = false
                        )
                    }

                    is Resource.Error -> {
                        updateState { copy(showRetryButton = true) }
                        sendEffect(HomeSideEffect.ShowError(result.error.toMessage()))
                    }
                }
            }
        }
    }
}