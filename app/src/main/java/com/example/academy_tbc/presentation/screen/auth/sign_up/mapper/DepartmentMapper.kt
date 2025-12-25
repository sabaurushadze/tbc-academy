package com.example.academy_tbc.presentation.screen.auth.sign_up.mapper

import com.example.academy_tbc.domain.model.auth.departments.Department
import com.example.academy_tbc.presentation.screen.auth.sign_up.model.DepartmentUi

fun Department.toPresentation() =
    DepartmentUi(
        id = id,
        name = name
    )