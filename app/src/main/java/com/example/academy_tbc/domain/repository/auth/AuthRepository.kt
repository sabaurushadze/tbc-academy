package com.example.academy_tbc.domain.repository.auth

import com.example.academy_tbc.domain.model.auth.User
import com.example.academy_tbc.domain.resource.Resource
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    val currentUser: Flow<User?>
    val currentUserId: String
    fun hasUser(): Boolean
    fun getUserProfile(): User?

    fun signInWithGoogle(idToken: String): Flow<Resource<Unit>>
    fun signIn(email: String, password: String): Flow<Resource<Unit>>
    fun signUp(email: String, password: String): Flow<Resource<Unit>>
    fun signOut(): Flow<Resource<Unit>>
    fun deleteAccount(): Flow<Resource<Unit>>
}
