package com.example.academy_tbc.data.auth

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.example.academy_tbc.data.network.AuthApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

interface AppContainer {
    val authRepository: AuthRepository
    val userTokenRepository: UserTokenRepository
}

class DefaultAppContainer(private val dataStore: DataStore<Preferences>) : AppContainer {

    private val client by lazy {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        OkHttpClient.Builder().addInterceptor { chain ->
                val token = runBlocking {
                    userTokenRepository.getToken.first()
                }
                val newRequest =
                    chain.request().newBuilder().addHeader("x-api-key", "reqres-free-v1")
                        .addHeader("Authorization", "Bearer $token").build()
                chain.proceed(newRequest)
            }.addInterceptor(logging).build()
    }

    val json = Json { ignoreUnknownKeys = true }
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(BASE_URL).client(client).build()

    private val retrofitService: AuthApiService by lazy {
        retrofit.create(AuthApiService::class.java)
    }

    override val authRepository: AuthRepository by lazy {
        NetworkAuthRepository(retrofitService)
    }
    override val userTokenRepository: UserTokenRepository by lazy {
        UserTokenRepository(dataStore)
    }

    companion object {
        const val BASE_URL = "https://reqres.in/api/"
    }

}