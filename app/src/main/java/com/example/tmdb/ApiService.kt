package com.example.tmdb

import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET("users")
    fun list(): Call<List<User>>
}