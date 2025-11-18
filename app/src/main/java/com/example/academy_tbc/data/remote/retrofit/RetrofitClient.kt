package com.example.academy_tbc.data.remote.retrofit

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

object RetrofitClient {
    private const val BASE_URL = "https://reqres.in/api/"
    private val client by lazy {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        OkHttpClient.Builder().addInterceptor { chain ->
            val newRequest =
                chain.request().newBuilder().addHeader("x-api-key", "reqres-free-v1").build()

            chain.proceed(newRequest)
        }.addInterceptor(logging).build()
    }

    val json = Json { ignoreUnknownKeys = true }
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(BASE_URL).client(client).build()

    val logInApiService: LogInApiService = retrofit.create(LogInApiService::class.java)
    val registerApiService: RegisterApiService = retrofit.create(RegisterApiService::class.java)
    val usersApiService: UsersApiService = retrofit.create(UsersApiService::class.java)

}