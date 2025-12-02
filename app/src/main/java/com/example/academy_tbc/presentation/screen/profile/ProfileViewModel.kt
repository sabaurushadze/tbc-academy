package com.example.academy_tbc.presentation.screen.profile

import androidx.lifecycle.viewModelScope
import com.example.academy_tbc.data.preferences.PreferenceKeys
import com.example.academy_tbc.domain.repository.datastore.DataStoreRepository
import com.example.academy_tbc.presentation.common.view.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository,
) : BaseViewModel<ProfileState, ProfileSideEffect, ProfileEvent>(ProfileState()) {

    override fun onEvent(event: ProfileEvent) {
        when (event) {
            ProfileEvent.RemoveUserToken -> removeUserToken()
            is ProfileEvent.GetUserEmail -> getUserEmail()
        }
    }

    private fun getUserEmail() {
        viewModelScope.launch {
            val email = dataStoreRepository.getOnce(PreferenceKeys.USER_EMAIL, "")
            updateState { copy(email = email) }
        }
    }

    private fun removeUserToken() {
        viewModelScope.launch {
            dataStoreRepository.clear()
            sendEffect((ProfileSideEffect.NavigateToLogIn))
        }
    }
}