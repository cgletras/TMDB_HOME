package com.example.tmdb.data

import com.google.gson.annotations.SerializedName

data class GenreList (
    val genres: List<Genre>
)

data class Genre (
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)