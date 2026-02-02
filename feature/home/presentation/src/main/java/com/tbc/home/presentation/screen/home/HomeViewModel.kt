package com.tbc.home.presentation.screen.home

import androidx.lifecycle.viewModelScope
import com.tbc.core.domain.util.onFailure
import com.tbc.core.domain.util.onSuccess
import com.tbc.core.presentation.base.BaseViewModel
import com.tbc.core.presentation.mapper.toStringResId
import com.tbc.home.domain.usecase.form.GetFormsUseCase
import com.tbc.home.presentation.mapper.form.toPresentation
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