package com.example.academy_tbc.data.repository.auth

import com.example.academy_tbc.data.common.FirebaseResponseHandler
import com.example.academy_tbc.data.mapper.auth.toDomainUser
import com.example.academy_tbc.domain.common.Resource
import com.example.academy_tbc.domain.model.auth.AuthError
import com.example.academy_tbc.domain.model.auth.User
import com.example.academy_tbc.domain.repository.auth.AuthRepository
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import com.google.firebase.auth.userProfileChangeRequest
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseAuthRepositoryImpl @Inject constructor(
    private val firebaseHandler: FirebaseResponseHandler,
) : AuthRepository {

    override val currentUser: Flow<User?>
        get() = callbackFlow {
            val listener =
                FirebaseAuth.AuthStateListener { auth ->
                    this.trySend(auth.currentUser?.toDomainUser())
                }
            Firebase.auth.addAuthStateListener(listener)
            awaitClose { Firebase.auth.removeAuthStateListener(listener) }
        }

    override val currentUserId: String
        get() = Firebase.auth.currentUser?.uid.orEmpty()

    override fun hasUser(): Boolean {
        return Firebase.auth.currentUser != null
    }

    override fun getUserProfile(): User? {
        return Firebase.auth.currentUser?.toDomainUser()
    }

    override fun signInWithGoogle(idToken: String): Flow<Resource<Unit, AuthError>> {
        return firebaseHandler.safeCall {
            val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)
            Firebase.auth.signInWithCredential(firebaseCredential).await()
        }
    }

    override fun signIn(email: String, password: String): Flow<Resource<Unit, AuthError>> {
        return firebaseHandler.safeCall {
            Firebase.auth.signInWithEmailAndPassword(email, password).await()
        }
    }


    override fun signUp(email: String, password: String): Flow<Resource<Unit, AuthError>> {
        return firebaseHandler.safeCall {
            Firebase.auth.createUserWithEmailAndPassword(email, password).await()
        }
    }

    override fun updateProfile(
        displayName: String,
    ): Flow<Resource<Unit, AuthError>> {
        return firebaseHandler.safeCall {
            val user = Firebase.auth.currentUser

            val request = userProfileChangeRequest {
                displayName.let { this.displayName = it }
            }
            user?.updateProfile(request)?.await()
        }
    }

    override fun signOut(): Flow<Resource<Unit, AuthError>> {
        return firebaseHandler.safeCall {
            Firebase.auth.signOut()
        }
    }


    override fun deleteAccount(): Flow<Resource<Unit, AuthError>> {
        return firebaseHandler.safeCall {
            Firebase.auth.currentUser!!.delete().await()
        }
    }
}