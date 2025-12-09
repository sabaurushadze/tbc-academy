package com.example.academy_tbc.data.service.util

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        val newRequest = chain.request().newBuilder()
            .addHeader("x-api-key", "reqres_ee9cba06994043309236e823d4968682")
            .build()

        return chain.proceed(newRequest)
    }
}