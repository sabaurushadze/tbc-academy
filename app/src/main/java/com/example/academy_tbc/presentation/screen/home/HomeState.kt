package com.example.academy_tbc.presentation.screen.home

import com.example.academy_tbc.presentation.screen.home.model.form.UiForm

data class HomeState(
    val groups:  List<List<UiForm.UiField>> = listOf(),
    val isLoading: Boolean = false,
)