package com.tbc.home.presentation.screen.home

import com.tbc.home.presentation.model.form.UiForm

data class HomeState(
    val groups:  List<List<UiForm.UiField>> = listOf(),
    val isLoading: Boolean = false,
)