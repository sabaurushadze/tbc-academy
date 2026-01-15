package com.example.academy_tbc.data.repository.auth.login

import com.example.academy_tbc.data.remote.mapper.error.mapExceptionToSignInError
import com.example.academy_tbc.domain.common.DataError
import com.example.academy_tbc.domain.common.Resource
import com.example.academy_tbc.domain.repository.auth.login.LogInRepository
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class LogInRepositoryImpl @Inject constructor() : LogInRepository {
    override suspend fun logIn(email: String, password: String): Resource<Unit, DataError.Auth> {
        return try {
            Firebase.auth
                .signInWithEmailAndPassword(email, password)
                .await()
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Failure(error = mapExceptionToSignInError(e))
        }
    }
}
