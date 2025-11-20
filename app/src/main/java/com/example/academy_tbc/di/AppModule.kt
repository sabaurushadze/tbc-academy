package com.example.academy_tbc.di

import com.example.academy_tbc.data.remote.retrofit.LogInApiService
import com.example.academy_tbc.data.remote.retrofit.RegisterApiService
import com.example.academy_tbc.data.remote.retrofit.UsersApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit


@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    private const val BASE_URL = "https://reqres.in/api/"
    val json = Json { ignoreUnknownKeys = true }

    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor { chain ->
            val newRequest = chain.request().newBuilder()
                .addHeader("x-api-key", "reqres-free-v1")
                .build()
            chain.proceed(newRequest)
        }
        .build()
    }

    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .client(okHttpClient)
            .build()
    }

    @Provides
    fun provideLogInService(retrofit: Retrofit): LogInApiService {
        return retrofit.create(LogInApiService::class.java)
    }

    @Provides
    fun provideRegisterService(retrofit: Retrofit): RegisterApiService {
        return retrofit.create(RegisterApiService::class.java)
    }

    @Provides
    fun provideUsersService(retrofit: Retrofit): UsersApiService {
        return retrofit.create(UsersApiService::class.java)
    }
}