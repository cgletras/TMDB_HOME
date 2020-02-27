package com.example.tmdb.repository.remote

import com.example.tmdb.repository.remote.api.ApiService
import com.example.tmdb.repository.remote.api.ApiServicePixaBay
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInitializer {

    private val logger = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    private val okHttp = OkHttpClient.Builder().addInterceptor(logger).build()

    private val retrofit =
        Retrofit.Builder()
            .client(okHttp)
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    private val retrofitPixaBay =
        Retrofit.Builder()
            .client(okHttp)
            .baseUrl("https://pixabay.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    fun apiService() = retrofit.create(ApiService::class.java)

    fun apiServicePixaBay() = retrofitPixaBay.create(ApiServicePixaBay::class.java)

}