package com.example.academy_tbc.domain.validator

interface EmailValidator {
    fun isValid(email: String): Boolean
}