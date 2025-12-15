package com.example.academy_tbc.presentation.screen.profile

import androidx.lifecycle.viewModelScope
import com.example.academy_tbc.domain.common.Resource
import com.example.academy_tbc.domain.common.isLoading
import com.example.academy_tbc.domain.model.profile.ValidationError
import com.example.academy_tbc.domain.usecase.auth.GetCurrentUserUseCase
import com.example.academy_tbc.domain.usecase.auth.SignOutUseCase
import com.example.academy_tbc.domain.usecase.auth.UpdateUserUseCase
import com.example.academy_tbc.domain.usecase.profile.ValidateUserNameUseCase
import com.example.academy_tbc.presentation.common.view.BaseViewModel
import com.example.academy_tbc.presentation.screen.profile.mapper.toGenericString
import com.example.academy_tbc.presentation.screen.profile.mapper.toUi
import com.example.academy_tbc.presentation.screen.sign_in.mapper.toGenericString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val updateUserUseCase: UpdateUserUseCase,
    private val signOutUseCase: SignOutUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val validateUserNameUseCase: ValidateUserNameUseCase,
) : BaseViewModel<ProfileState, ProfileSideEffect, ProfileEvent>(ProfileState()) {

    override fun onEvent(event: ProfileEvent) {
        when (event) {
            ProfileEvent.SignOut -> signOut()
            is ProfileEvent.UpdateUserName -> updateName(event.userName)
        }
    }

    init {
        observeUser()
    }

    private fun validateUserName(userName: String): Boolean {
        val error = validateUserNameUseCase(userName)

        return if (error != ValidationError.UNKNOWN) {
            sendEffect(
                ProfileSideEffect.ShowError(
                    error.toGenericString()
                )
            )
            false
        } else {
            true
        }
    }

    private fun updateName(newName: String) {
        viewModelScope.launch {
            if (!validateUserName(newName)) return@launch
            updateUserUseCase(newName).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        updateState { copy(isLoading = result.isLoading()) }
                    }

                    is Resource.Success -> {
                        sendEffect(ProfileSideEffect.UpdateUserNameSuccess)
                        updateState {
                            copy(
                                isLoading = result.isLoading(),
                                user = user?.copy(displayName = newName)
                            )
                        }
                    }

                    is Resource.Error -> {
                        updateState { copy(isLoading = result.isLoading()) }
                        sendEffect(
                            ProfileSideEffect.ShowError(
                                result.error.toGenericString()
                            )
                        )
                    }
                }
            }
        }
    }

    private fun signOut() {
        viewModelScope.launch {
            signOutUseCase().collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        updateState { copy(isLoading = result.isLoading()) }
                    }

                    is Resource.Success -> {
                        updateState { copy(isLoading = result.isLoading(), user = null) }
                        sendEffect(ProfileSideEffect.NavigateToSignIn)
                    }

                    is Resource.Error -> {
                        updateState { copy(isLoading = result.isLoading()) }
                        sendEffect(
                            ProfileSideEffect.ShowError(result.error.toGenericString())
                        )
                    }
                }
            }
        }
    }

    fun observeUser() {
        viewModelScope.launch {
            getCurrentUserUseCase().collect { user ->
                updateState { copy(user = user?.toUi()) }
            }
        }
    }

}

