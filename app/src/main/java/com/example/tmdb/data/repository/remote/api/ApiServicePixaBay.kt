package com.example.tmdb.data.repository.remote.api

import com.example.tmdb.data.repository.remote.entity.GenreListImage
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServicePixaBay {
    @GET("api/")
    fun getGenreImage(@Query("key") key: String, @Query("q") q: String, @Query("image_type")image_type:String, @Query("pretty")pretty:String, @Query("orientation")orientation:String): Call<GenreListImage>
}