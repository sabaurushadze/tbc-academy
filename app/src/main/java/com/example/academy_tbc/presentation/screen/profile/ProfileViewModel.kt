package com.example.academy_tbc.presentation.screen.profile

import com.example.academy_tbc.domain.usecase.auth.sign_in.ValidateEmailUseCase
import com.example.academy_tbc.domain.usecase.auth.sign_in.ValidatePasswordUseCase
import com.example.academy_tbc.presentation.common.view.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
) : BaseViewModel<ProfileState, ProfileSideEffect, ProfileEvent>(ProfileState()) {


    override fun onEvent(event: ProfileEvent) {
        when(event) {

            else -> {}
        }

    }

}