package com.example.tmdb.repository.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieList(
    @SerializedName("results")
    val movies: List<Movie>
): Parcelable

@Parcelize
@Entity(tableName = "movie_table")
data class Movie (

    @PrimaryKey
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
    val poster: String,
    @SerializedName("instant")
    var instant:String

): Parcelable

enum class MediaType {
    MOVIE,
    SERIE
}