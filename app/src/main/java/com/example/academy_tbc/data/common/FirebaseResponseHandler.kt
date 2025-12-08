package com.example.academy_tbc.data.common

import com.example.academy_tbc.domain.resource.Resource
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FirebaseResponseHandler @Inject constructor() {

    fun <T : Any> safeFirebaseCall(call: suspend () -> T) = flow {
        emit(Resource.Loading(isLoading = true))

        try {
            val result = call()
            emit(Resource.Success(data = result))
        } catch (e: Throwable) {
            val errorMessage = when (e) {
                is com.google.firebase.FirebaseNetworkException -> e.localizedMessage
                    ?: "Check your connection"

                is FirebaseFirestoreException -> e.localizedMessage ?: "Firestore error"
                else -> e.localizedMessage ?: "Unknown error"
            }
            emit(Resource.Error(errorMessage))
        }
        emit(Resource.Loading(isLoading = false))
    }
}