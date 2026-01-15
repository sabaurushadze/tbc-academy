package com.example.academy_tbc.data.repository.auth.register

import com.example.academy_tbc.data.remote.mapper.error.mapExceptionToSignInError
import com.example.academy_tbc.domain.common.DataError
import com.example.academy_tbc.domain.common.Resource
import com.example.academy_tbc.domain.repository.auth.register.RegisterRepository
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class RegisterRepositoryImpl @Inject constructor() : RegisterRepository {
    override suspend fun register(email: String, password: String): Resource<Unit, DataError.Auth> {
        return try {
            Firebase.auth
                .createUserWithEmailAndPassword(email, password)
                .await()
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Failure(error = mapExceptionToSignInError(e))
        }
    }
}
