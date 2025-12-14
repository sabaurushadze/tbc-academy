package com.example.academy_tbc.domain.repository.auth

import com.example.academy_tbc.domain.common.AuthError
import com.example.academy_tbc.domain.model.auth.User
import com.example.academy_tbc.domain.common.Resource
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    val currentUser: Flow<User?>
    val currentUserId: String
    fun hasUser(): Boolean
    fun getUserProfile(): User?

    fun signInWithGoogle(idToken: String): Flow<Resource<Unit, AuthError>>
    fun signIn(email: String, password: String): Flow<Resource<Unit, AuthError>>
    fun signUp(email: String, password: String): Flow<Resource<Unit, AuthError>>
    fun signOut(): Flow<Resource<Unit, AuthError>>
    fun deleteAccount(): Flow<Resource<Unit, AuthError>>
}
