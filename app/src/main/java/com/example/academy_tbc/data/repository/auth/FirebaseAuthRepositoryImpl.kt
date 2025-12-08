package com.example.academy_tbc.data.repository.auth

import com.example.academy_tbc.data.common.FirebaseResponseHandler
import com.example.academy_tbc.data.mapper.auth.toDomainUser
import com.example.academy_tbc.domain.model.auth.User
import com.example.academy_tbc.domain.repository.auth.AuthRepository
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
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

    override fun signInWithGoogle(idToken: String) =
        firebaseHandler.safeFirebaseCall {
            val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)
            Firebase.auth.signInWithCredential(firebaseCredential).await()
            Unit
        }

    override fun signIn(email: String, password: String) =
        firebaseHandler.safeFirebaseCall {
            Firebase.auth.signInWithEmailAndPassword(email, password).await()
            Unit
        }


    override fun signUp(email: String, password: String) =
        firebaseHandler.safeFirebaseCall {
            Firebase.auth.createUserWithEmailAndPassword(email, password).await()
            Unit
        }

    override fun signOut() =
        firebaseHandler.safeFirebaseCall {
            Firebase.auth.signOut()
        }


    override fun deleteAccount() =
        firebaseHandler.safeFirebaseCall {
            Firebase.auth.currentUser!!.delete().await()
            Unit
        }
}