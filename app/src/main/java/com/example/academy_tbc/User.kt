package com.example.academy_tbc

data class User(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val birthday: String,
    val address: String,
    val email: String,
    val desc: String? = ""
)