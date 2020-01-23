package com.example.tmdb

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("name")
    val nome: String
)
