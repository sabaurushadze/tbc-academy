package com.example.academy_tbc.data.remote.network

import com.example.academy_tbc.domain.repository.datastore.DataStoreManager
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject


class AuthInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val requestBuilder = chain.request().newBuilder()
        requestBuilder.addHeader(
            "x-api-key",
            "reqres_ee9cba06994043309236e823d4968682"
        )


        return chain.proceed(requestBuilder.build())
    }
}