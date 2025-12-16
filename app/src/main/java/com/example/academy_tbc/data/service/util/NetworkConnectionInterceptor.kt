package com.example.academy_tbc.data.service.util

import com.example.academy_tbc.domain.observer.ConnectivityObserver
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject

class NetworkConnectionInterceptor @Inject constructor(
    private val connectivityObserver: ConnectivityObserver,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val isConnected = runBlocking {
            connectivityObserver.isConnected.first()
        }

        if (!isConnected) {
            throw IOException("No network connection")
        }

        return chain.proceed(chain.request())
    }
}