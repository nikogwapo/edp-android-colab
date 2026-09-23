package com.liceo.account.data.network

import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import java.util.concurrent.TimeUnit

object NetworkModule {
    // TODO 5a: put the base URL from "Meet the Exam API" between the quotes
    private const val BASE_URL = "https://6a9b85920ad174e139e8b0f2.mockapi.io/chat-messaging/api/v1/"

    private val json = Json {
        // TODO 5b: set ignoreUnknownKeys and coerceInputValues to true
        ignoreUnknownKeys = true
        coerceInputValues = true
    }

    // GIVEN (read it, do not change it): prints every request in Logcat
    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(15, TimeUnit.SECONDS)
        .build()

    val userApi: UserApiService = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        // TODO 5c: finish the chain with .build() and .create(...)
        .build()
        .create(UserApiService::class.java)
}
