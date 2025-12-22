package com.example.academy_tbc.presentation.screen.home

import android.os.SystemClock
import androidx.lifecycle.viewModelScope
import com.example.academy_tbc.domain.model.auth.sign_up.SignUpValidationError
import com.example.academy_tbc.domain.usecase.auth.sign_up.ValidateFirstNameUseCase
import com.example.academy_tbc.domain.usecase.auth.sign_up.ValidateLastNameUseCase
import com.example.academy_tbc.domain.usecase.auth.sign_up.ValidateOtpCodeUseCase
import com.example.academy_tbc.domain.usecase.auth.sign_up.ValidatePhoneNumberUseCase
import com.example.academy_tbc.domain.usecase.auth.sign_up.ValidateSignUpConfirmPasswordUseCase
import com.example.academy_tbc.domain.usecase.auth.sign_up.ValidateSignUpEmailUseCase
import com.example.academy_tbc.domain.usecase.auth.sign_up.ValidateSignUpPasswordUseCase
import com.example.academy_tbc.presentation.common.view.BaseViewModel
import com.example.academy_tbc.presentation.screen.auth.sign_up.mapper.toGenericString
import com.example.academy_tbc.presentation.util.GenericString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
) : BaseViewModel<HomeState, HomeSideEffect, HomeEvent>(HomeState()) {


    override fun onEvent(event: HomeEvent) {

    }


}