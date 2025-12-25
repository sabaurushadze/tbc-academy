package com.example.academy_tbc.data.network

import com.example.academy_tbc.domain.preferences.AppPreferencesKeys
import com.example.academy_tbc.domain.repository.datastore.DataStoreManager
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val dataStoreManager: DataStoreManager
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val token = runBlocking {
            dataStoreManager
                .getPreference(AppPreferencesKeys.TOKEN, "")
                .first()
        }

        val requestBuilder = chain.request().newBuilder()

        if (token.isNotBlank()) {
            requestBuilder.addHeader(
                "Authorization",
                "Bearer $token"
            )
        }

        return chain.proceed(requestBuilder.build())
    }
}
