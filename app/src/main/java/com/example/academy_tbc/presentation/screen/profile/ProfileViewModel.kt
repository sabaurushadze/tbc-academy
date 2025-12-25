package com.example.academy_tbc.presentation.screen.profile

import androidx.lifecycle.viewModelScope
import com.example.academy_tbc.domain.preferences.AppPreferencesKeys
import com.example.academy_tbc.domain.usecase.datastore.GetPreferenceUseCase
import com.example.academy_tbc.domain.usecase.datastore.RemovePreferencesUseCase
import com.example.academy_tbc.presentation.common.view.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getPreferenceUseCase: GetPreferenceUseCase,
    private val removePreferencesUseCase: RemovePreferencesUseCase,
) : BaseViewModel<ProfileState, ProfileSideEffect, ProfileEvent>(ProfileState()) {


    init {
        getProfileInfo()
    }

    override fun onEvent(event: ProfileEvent) {
        when (event) {
            ProfileEvent.SignOut -> signOut()
        }

    }

    private fun signOut() {
        viewModelScope.launch {
            removePreferencesUseCase.invoke(
                listOf(
                    AppPreferencesKeys.FULL_NAME,
                    AppPreferencesKeys.EMAIL,
                    AppPreferencesKeys.DEPARTMENT,
                    AppPreferencesKeys.TOKEN
                )
            )
            emitSideEffect(ProfileSideEffect.NavigateToSignIn)
        }
    }

    private fun getProfileInfo() {
        viewModelScope.launch {
            val fullName = getPreferenceUseCase.invoke(AppPreferencesKeys.FULL_NAME, "").first()
            val email = getPreferenceUseCase.invoke(AppPreferencesKeys.EMAIL, "").first()
            val department = getPreferenceUseCase.invoke(AppPreferencesKeys.DEPARTMENT, "").first()

            updateUiState {
                copy(
                    fullName = fullName,
                    email = email,
                    department = department
                )
            }
        }
    }

}