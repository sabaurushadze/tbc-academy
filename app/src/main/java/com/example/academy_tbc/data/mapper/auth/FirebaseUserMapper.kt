package com.example.academy_tbc.data.mapper.auth

import com.example.academy_tbc.domain.model.auth.User
import com.google.firebase.auth.FirebaseUser

fun FirebaseUser?.toDomainUser(): User {
    return if (this == null) User() else User(
        id = this.uid,
        email = this.email.orEmpty(),
        displayName = this.displayName.orEmpty(),
        photoUrl = photoUrl.toString()
    )
}