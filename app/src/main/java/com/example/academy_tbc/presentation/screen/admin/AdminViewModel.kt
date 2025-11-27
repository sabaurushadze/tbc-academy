package com.example.academy_tbc.presentation.screen.admin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.academy_tbc.R
import com.example.academy_tbc.data.datastore.UserSettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

@HiltViewModel
class AdminViewModel @Inject constructor(
    private val adminValidations: AdminValidations,
    private val userSettingsRepository: UserSettingsRepository,
) : ViewModel() {
    val userSettings = userSettingsRepository.userSettings

    private val _sideEffect = MutableSharedFlow<AdminSideEffect>()
    val sideEffect = _sideEffect.asSharedFlow()

    fun onEvent(event: AdminEvent) {
        when (event) {
            is AdminEvent.SaveUser -> saveUser(
                firstName = event.firstName,
                lastName = event.lastName,
                email = event.email
            )
        }
    }

    private fun saveUser(firstName: String, lastName: String, email: String) {
        viewModelScope.launch {
            if (adminValidations.validateAll(
                    firstName = firstName,
                    lastName = lastName,
                    email = email
                )
            ) {
                userSettingsRepository.saveUserSettings(firstName, lastName, email)
                _sideEffect.emit(AdminSideEffect.Success)
            } else {
                _sideEffect.emit(AdminSideEffect.ShowError(R.string.please_fill_all_fields))
            }
        }
    }
}
