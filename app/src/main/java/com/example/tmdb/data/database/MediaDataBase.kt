package com.example.tmdb.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.tmdb.data.repository.local.dao.FavoriteMediaDao
import com.example.tmdb.data.repository.remote.entity.Movie
import com.example.tmdb.data.repository.remote.entity.Serie
import com.example.tmdb.data.repository.local.dao.MovieDao
import com.example.tmdb.data.repository.local.dao.SerieDao
import com.example.tmdb.data.repository.local.entity.FavoriteMedia

@Database(entities = [Movie::class, Serie::class, FavoriteMedia::class], version = 1, exportSchema = false)
abstract class MediaDataBase: RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun serieDao(): SerieDao
    abstract fun favoriteMediaDao():FavoriteMediaDao
}