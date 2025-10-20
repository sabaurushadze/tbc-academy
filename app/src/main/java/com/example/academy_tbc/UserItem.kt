package com.example.academy_tbc

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserItem(
    val firstName: String,
    val lastName: String,
    val age: Int?,
    val email: String,
) : Parcelable