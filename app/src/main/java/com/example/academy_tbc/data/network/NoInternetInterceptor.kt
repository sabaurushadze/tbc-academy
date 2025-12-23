package com.example.academy_tbc.data.network

import com.example.academy_tbc.data.manager.NetworkStateManager
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject

class NoInternetInterceptor @Inject constructor(
    private val connectivityObserver: NetworkStateManager,
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