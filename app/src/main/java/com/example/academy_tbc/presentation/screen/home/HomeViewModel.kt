package com.example.academy_tbc.presentation.screen.home

import androidx.lifecycle.viewModelScope
import com.example.academy_tbc.domain.common.onFailure
import com.example.academy_tbc.domain.common.onSuccess
import com.example.academy_tbc.domain.usecase.form.GetFormsUseCase
import com.example.academy_tbc.presentation.common.BaseViewModel
import com.example.academy_tbc.presentation.screen.home.mapper.form.toPresentation
import com.example.academy_tbc.presentation.util.toStringResId
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getFormsUseCase: GetFormsUseCase,
) : BaseViewModel<HomeState, HomeSideEffect, HomeEvent>(HomeState()) {

    override fun onEvent(event: HomeEvent) {
        when (event) {
            HomeEvent.GetForms -> getForms()
        }
    }

    private fun getForms() {
        viewModelScope.launch {
            getFormsUseCase()
                .onSuccess { groups ->
                    val uiGroups = groups.map { group ->
                        group.map { it.toPresentation() }
                    }
                    updateState { copy(groups = uiGroups) }
                }
                .onFailure { emitSideEffect(HomeSideEffect.ShowSnackBar(errorRes = it.toStringResId())) }
        }
    }

}