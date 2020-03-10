package com.example.tmdb.data.repository.local.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "favorites_table")
data class FavoriteMedia (
    @PrimaryKey
    @SerializedName("id")
    val id: Int,
    @SerializedName("name", alternate = ["title"])
    val title: String,
    @SerializedName("poster_path")
    val poster: String?,
    @SerializedName("overview")
    val details: String,
    @SerializedName("backdrop_path")
    val posterWide: String?,
    @SerializedName("first_air_date", alternate = ["release_date"])
    val releaseDate: String,
    @SerializedName("instant")
    var instant: String?

): Parcelable