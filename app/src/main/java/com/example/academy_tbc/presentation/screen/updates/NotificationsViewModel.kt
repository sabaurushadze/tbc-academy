package com.example.academy_tbc.presentation.screen.updates

import com.example.academy_tbc.domain.usecase.auth.sign_in.ValidateEmailUseCase
import com.example.academy_tbc.domain.usecase.auth.sign_in.ValidatePasswordUseCase
import com.example.academy_tbc.presentation.common.view.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NotificationsViewModel @Inject constructor(
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
) : BaseViewModel<NotificationsState, NotificationsSideEffect, NotificationsEvent>(NotificationsState()) {


    override fun onEvent(event: NotificationsEvent) {
        when(event) {

            else -> {}
        }

    }

}