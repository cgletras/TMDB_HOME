package com.example.tmdb.data

import com.google.gson.annotations.SerializedName

data class GenreList (
    @SerializedName("genres")
    val genres: List<Genre>
)

data class GenreListImage(
    @SerializedName("hits")
    val genresPixabay: List<Genre>
)

data class Genre (
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("webformatURL")
    val genreImage: String
)