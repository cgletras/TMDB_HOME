package com.example.tmdb.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class MediaList(
    val medias: ArrayList<Media>
)

@Parcelize
data class Media(
    val id: Int,
    val title: String,
    val poster: String?,
    val posterWide: String?,
    val details: String,
    val releaseDate: String,
    var instant: String?,
    val mediaType: MediaType
):Parcelable

enum class MediaType{
    SERIE,
    MOVIE
}