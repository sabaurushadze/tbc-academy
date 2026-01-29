package com.example.academy_tbc.presentation.screen.home

import androidx.lifecycle.viewModelScope
import com.example.academy_tbc.domain.common.onFailure
import com.example.academy_tbc.domain.common.onSuccess
import com.example.academy_tbc.domain.usecase.post.GetPostsUseCase
import com.example.academy_tbc.domain.usecase.story.GetStoriesUseCase
import com.example.academy_tbc.presentation.common.BaseViewModel
import com.example.academy_tbc.presentation.screen.home.mapper.post.toPresentation
import com.example.academy_tbc.presentation.screen.home.mapper.story.toPresentation
import com.example.academy_tbc.presentation.util.toStringResId
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getStoriesUseCase: GetStoriesUseCase,
    private val getPostsUseCase: GetPostsUseCase,
) : BaseViewModel<HomeState, HomeSideEffect, HomeEvent>(HomeState()) {

    override fun onEvent(event: HomeEvent) {
        when (event) {
            HomeEvent.GetStories -> getStories()
            HomeEvent.GetPosts -> getPosts()
        }
    }

    private fun getPosts() {
        viewModelScope.launch {
            getPostsUseCase()
                .onSuccess { postsDomain ->
                    updateState { copy(posts = postsDomain.map { it.toPresentation() }) }
                }
                .onFailure { emitSideEffect(HomeSideEffect.ShowSnackBar(errorRes = it.toStringResId())) }
        }
    }

    private fun getStories() {
        viewModelScope.launch {
            getStoriesUseCase()
                .onSuccess { storiesDomain ->
                    updateState { copy(stories = storiesDomain.map { it.toPresentation() }) }
                }
                .onFailure { emitSideEffect(HomeSideEffect.ShowSnackBar(errorRes = it.toStringResId())) }
        }
    }


}