package com.restaurantfinder.network.utils

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

object NetworkUtils {


    fun createRetrofit(okHttpClient: OkHttpClient): Retrofit {

        val json = Json {
            isLenient = true
            ignoreUnknownKeys = true
        }

        return Retrofit.Builder()
            .baseUrl(NetworkConstants.BASE_URL)
            .addConverterFactory(json.asConverterFactory(MediaType.get("application/json")))
            .client(okHttpClient)
            .build()
    }

    fun createOkHttpClient(interceptor: Interceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .callTimeout(1, TimeUnit.MINUTES)
            .readTimeout(1, TimeUnit.MINUTES)
            .build()
    }

    fun createHttpInterceptor(): Interceptor {
        return Interceptor { chain ->
            val newRequest = chain.request().newBuilder()
                .header(NetworkConstants.API_KEY_USER_ID, NetworkConstants.USER_KEY)
                .build()


             chain.proceed(newRequest)
        }
    }
}