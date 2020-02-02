package com.example.tmdb.data

import com.google.gson.annotations.SerializedName

data class MovieList(
    @SerializedName("results")
    val movies: List<Movie>
)

data class Movie (

    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("backdrop_path")
    val poster: String?,
    @SerializedName("release_date")
    val releaseDate: String

)
