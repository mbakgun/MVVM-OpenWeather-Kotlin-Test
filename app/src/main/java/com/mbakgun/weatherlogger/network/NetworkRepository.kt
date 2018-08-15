package com.mbakgun.weatherlogger.network

import com.mbakgun.weatherlogger.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkRepository {
    private val baseUrl = "https://api.openweathermap.org"
    val id = "745044"
    val appId = "7dddf551a056642a1ae589fdb97aa5ec"
    private val client = OkHttpClient().newBuilder()
            .cache(null)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
            })
            .build()
    val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    val weatherService = retrofit.create(WeatherService::class.java)
}