package com.example.tmdb.repository.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SerieList(
    @SerializedName("results")
    val series: List<Serie>
) : Parcelable

@Parcelize
@Entity(tableName = "serie_table")
data class Serie(

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
    val posterWide: String,
    @SerializedName("instant")
    var instant: String?

) : Parcelable