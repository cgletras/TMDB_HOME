package com.example.tmdb.data

import com.google.gson.annotations.SerializedName

data class SerieList(
    @SerializedName("results")
    val series: List<Serie>
)

data class Serie (

    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val title: String,
    @SerializedName("poster_path")
    val poster: String?,
    @SerializedName("overview")
    val details: String,
    @SerializedName("backdrop_path")
    val posterWide: String
)