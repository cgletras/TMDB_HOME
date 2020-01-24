package com.example.tmdb

import com.google.gson.annotations.SerializedName

class Movie (

    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String
)
