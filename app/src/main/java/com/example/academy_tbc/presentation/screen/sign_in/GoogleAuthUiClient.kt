package com.example.academy_tbc.presentation.screen.sign_in

import android.content.Context
import android.util.Log
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import com.example.academy_tbc.R
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential.Companion.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL

class GoogleAuthUiClient @Inject constructor(
    private val context: Context
) {

    private val credentialManager = CredentialManager.create(context)
    private val auth = FirebaseAuth.getInstance()

    suspend fun signIn(): SignInResult {
        return try {
            Log.d("AUTH_FLOW", "Starting Google Sign-In")
            val googleIdOption = GetGoogleIdOption.Builder()
                .setServerClientId(context.getString(R.string.web_client_id))
                .setFilterByAuthorizedAccounts(false)
                .build()

            val request = GetCredentialRequest.Builder()
                .addCredentialOption(googleIdOption)
                .build()

            val result = credentialManager.getCredential(
                context = context,
                request = request
            )
            Log.d("AUTH_FLOW", "CredentialManager success: $result")


            val credential = result.credential

            if (credential.type == TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                val idToken = googleIdTokenCredential.idToken

                Log.d("AUTH_FLOW", "Google ID Token = $idToken")

                val firebaseCred = GoogleAuthProvider.getCredential(idToken, null)
                val user = auth.signInWithCredential(firebaseCred).await().user

                Log.d("AUTH_FLOW", "Firebase User = ${user?.email}")

                return SignInResult(
                    data = UserData(
                        userId = user?.uid,
                        username = user?.displayName,
                        profilePictureUrl = user?.photoUrl?.toString()
                    ),
                    errorMessage = null
                )
            } else {
                Log.e("AUTH_FLOW", "Credential is NOT Google ID token!")
                return SignInResult(null, "Invalid credential type")
            }
        } catch (e: Exception) {
            Log.e("AUTH_FLOW", "Sign-in failed", e)
            SignInResult(null, e.localizedMessage)
        }
    }

    fun signOut() {
        auth.signOut()
    }

    fun getSignedInUser() =
        auth.currentUser?.let {
            UserData(
                it.uid,
                it.displayName,
                it.photoUrl?.toString()
            )
        }
}


data class UserData(
    val userId: String?,
    val username: String?,
    val profilePictureUrl: String?
)

data class SignInResult(
    val data: UserData?,
    val errorMessage: String?
)