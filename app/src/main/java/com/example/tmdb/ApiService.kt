package com.example.tmdb

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {


    @GET("users")
    fun listUsers(): Call<List<User>>

    @GET("genre/movie/list")
    fun listGenres(@Query("api_key") apiKey: String, @Query("language") lang: String): Call<GenreList>
}