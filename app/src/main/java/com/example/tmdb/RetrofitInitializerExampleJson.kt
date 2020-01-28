package com.example.tmdb

import com.example.tmdb.service.ApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInitializerExampleJson {

    private val retrofit =
        Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    fun apiService() = retrofit.create(ApiService::class.java)


}