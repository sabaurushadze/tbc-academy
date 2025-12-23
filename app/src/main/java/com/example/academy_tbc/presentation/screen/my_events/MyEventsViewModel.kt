package com.example.academy_tbc.presentation.screen.my_events

import com.example.academy_tbc.domain.usecase.auth.sign_in.validation.ValidateEmailUseCase
import com.example.academy_tbc.domain.usecase.auth.sign_in.validation.ValidatePasswordUseCase
import com.example.academy_tbc.presentation.common.view.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyEventsViewModel @Inject constructor(
) : BaseViewModel<MyEventsState, MyEventsSideEffect, MyEventsEvent>(MyEventsState()) {


    override fun onEvent(event: MyEventsEvent) {
        when(event) {

            else -> {}
        }

    }

}