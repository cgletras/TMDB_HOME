package com.example.tmdb.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieList(
    @SerializedName("results")
    val movies: List<Movie>
): Parcelable

@Parcelize
data class Movie (

    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("backdrop_path")
    val posterBack: String?,
    @SerializedName("release_date")
    val releaseDate: String,
    @SerializedName("overview")
    val details: String,
    @SerializedName("poster_path")
    val poster: String

): Parcelable
