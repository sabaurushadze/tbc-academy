package com.example.academy_tbc.data.service

import com.example.academy_tbc.data.preferences.PreferenceKeys
import com.example.academy_tbc.domain.repository.datastore.DataStoreRepository
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject


class AuthInterceptor @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = runBlocking {
            dataStoreRepository.getOnce(
                key = PreferenceKeys.USER_TOKEN,
                defaultValue = ""
            )
        }

        val newRequest = chain.request().newBuilder()
            .addHeader("x-api-key", "reqres_ee9cba06994043309236e823d4968682")
            .apply {
                if (token.isNotEmpty()) {
                    addHeader("Authorization", "Bearer $token")
                }
            }
            .build()

        return chain.proceed(newRequest)
    }
}