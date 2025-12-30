package com.example.academy_tbc.presentation.screen.categories

import androidx.lifecycle.viewModelScope
import com.example.academy_tbc.domain.usecase.equipment.GetEquipmentUseCase
import com.example.academy_tbc.presentation.common.mapper.toGenericString
import com.example.academy_tbc.presentation.common.view.BaseViewModel
import com.example.academy_tbc.presentation.screen.categories.mapper.toPresentation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val getEquipmentUseCase: GetEquipmentUseCase,
) : BaseViewModel<CategoryState, CategorySideEffect, CategoryEvent>(CategoryState()) {

    private val searchQuery = MutableStateFlow("")


    init {
        initSearchObserver()
    }

    override fun onEvent(event: CategoryEvent) {
        when (event) {
            is CategoryEvent.FetchEquipmentEvent -> search(event.filteredName)
        }
    }

    @OptIn(FlowPreview::class)
    private fun initSearchObserver() {
        viewModelScope.launch {
            searchQuery.debounce(300).collect {
                getEquipment(query = it)
            }
        }
    }

    private fun search(query: String) {
        searchQuery.value = query
    }


    private fun getEquipment(query: String) {
        handleResponse(
            apiCall = { getEquipmentUseCase() },
            onLoading = { updateState { copy(isLoading = it) } },
            onSuccess = { equipmentList ->
                val uiList = equipmentList.flatMap { it.toPresentation() }.let { list ->
                    if (query.isBlank()) list
                    else list.filter {
                        it.name.contains(query, ignoreCase = true)
                    }
                }
                updateState {
                    copy(equipments = uiList)
                }
            },
            onError = { emitSideEffect(CategorySideEffect.ShowError(it.toGenericString())) })
    }
}