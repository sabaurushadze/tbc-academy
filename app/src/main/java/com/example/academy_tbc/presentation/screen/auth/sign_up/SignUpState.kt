package com.example.academy_tbc.presentation.screen.auth.sign_up

import com.example.academy_tbc.presentation.screen.auth.sign_up.model.DepartmentUi

data class SignUpState(
    val isLoading: Boolean = false,
    val isOtpVisible: Boolean = false,
    val elapsedTime: Long = 0,
    val selectedDepartment: Int? = null,
    val departments: List<DepartmentUi>? = null
)